package com.dome;

import com.dome.bean.Data;
import com.dome.bean.Handler;
import com.dome.bean.Param;
import com.dome.bean.View;
import com.dome.helper.*;
import com.dome.util.*;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * 前端控制器 —— 最重要的一个类 ，控制着整个请求的执行流程
 *
 *    又称为请求转发器
 * @author hzq
 */
@WebServlet(value = "/*",loadOnStartup = 0)
public class DispatcherServlet extends HttpServlet {

    @Override
    public void init(ServletConfig servletConfig)    throws ServletException {
//         初始化helper
        HelperLoader.init();
//        注册Servlet
        ServletContext servletContext = servletConfig.getServletContext();
//        注册处理jsp 的Servlet
        ServletRegistration jspServlet = servletContext.getServletRegistration("jsp");
        jspServlet.addMapping(ConfigHelper.getJsp() + "*");
//        注册处理静态资源的默认的Servlet
        ServletRegistration defaultServlet = servletContext.getServletRegistration("default");
        defaultServlet.addMapping(ConfigHelper.getAsset() + "*");
//        初始化操作文件上传的对象
        UploadHelper.init(servletContext);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//       1.初始化SessionHelper
        ServletHelper.init(req, resp);
        try {
//            得到请求的方式
            String requestMethod = req.getMethod().toLowerCase();
//            获得请求的路径
            String requestPath = req.getPathInfo();

            if ("/favicon.ico".equals(requestPath)){
                return;
            }

//        2.获得处理 Handler
            Handler handler = ControllerHelper.getHandler(requestMethod, requestPath);

            excu(req, resp, handler);
        }catch (Exception e){
            throw  new RuntimeException(e);
        }finally {
            ServletHelper.destroy();
        }
    }

    private void excu(HttpServletRequest req, HttpServletResponse resp, Handler handler) throws Exception {
        //            3.得到Param
        if (handler != null){
//            获取controller 实体
             Class<?> controllerClass = handler.getControllerClass();
//             得到Controller 对象 —— 得到的可能是代理Controller 对象 增强
             Object controllerBean = BeanHelper.getBean(controllerClass);

            Param param;
                if (UploadHelper.isMultipart(req)){
//                    文件上传的Param 对象
                    param =   UploadHelper.createParam(req);
                }else {
//                    一般请求的Param对象
                    param = RequestHelper.createParam(req);
                }
            excuteMethod(req, resp, handler, controllerBean, param);
        }
    }

    private void excuteMethod(HttpServletRequest req, HttpServletResponse resp, Handler handler, Object controllerBean, Param param) throws IOException, ServletException {
        //                    4.执行方法
        //            得到请求方法的名字
        Method actionMethod = handler.getActionMethod();
        Object result;
        //            根据是否有参数 判断是传递参数给方法执行
        if (param.isEmpty()){
             result = ReflectionUtil.invokeMethod(controllerBean,actionMethod);
        }else {
             result = ReflectionUtil.invokeMethod(controllerBean, actionMethod, param);
        }
        if (result instanceof View){
            handView(req, resp, (View) result);
        }else if (result instanceof Data){
            handData(resp, (Data) result);
        }
    }

    private void handView(HttpServletRequest req, HttpServletResponse resp, View result) throws IOException, ServletException {
        //                返回jsp页面
        String path = result.getPath();
        if (StringUtil.isNotEmpty(path)){

            if (path.startsWith("/")){
//                        如何区分？
//                        重定向
                resp.sendRedirect( req.getContextPath() + path);
            }else {
//                        转发
                Map<String, Object> model = result.getModel();
                for (Map.Entry<String, Object> stringObjectEntry : model.entrySet()) {
                    req.setAttribute(stringObjectEntry.getKey(),stringObjectEntry.getValue());
                }
                req.getRequestDispatcher(ConfigHelper.getJsp() + path).forward(req, resp);
            }
        }
    }

    private void handData(HttpServletResponse resp, Data result) throws IOException {
        //               返回JSON 数据
        Object model = result.getModel();
        if (model != null){
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            PrintWriter writer = resp.getWriter();
            String json = JsonUtil.toJson(model);
            writer.write(json);
            writer.flush();
            writer.close();
        }
    }

}
