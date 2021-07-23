package com.dome.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet 助手类 —— 用于封装 req 和 resp 对象给Controller 使用  降低 DispatcherServlet 的耦合性
 * @author hzq
 */
public class ServletHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServletHelper.class);

    /**
     * 每个线程一个独有的ServletHelper 对象
     */
    private static final ThreadLocal<ServletHelper> SERVLER_HELPER_HOLOER = new ThreadLocal<ServletHelper>();

    private HttpServletRequest request;
    private HttpServletResponse response;

    public ServletHelper(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    /**
     * 初始化
     * @param request
     * @param response
     */
    public static void init(HttpServletRequest request, HttpServletResponse response){
        SERVLER_HELPER_HOLOER.set(new ServletHelper(request,response));
    }

    /**
     * 得到req 对象
     * @return
     */
    private static HttpServletRequest getRequest() {
        return SERVLER_HELPER_HOLOER.get().request;
    }

    /**
     * 得到resp 对象
     * @return
     */
    private static HttpServletResponse getResponse() {
        return SERVLER_HELPER_HOLOER.get().response;
    }

    /**
     * 销毁
     */
    public static void destroy(){
        SERVLER_HELPER_HOLOER.remove();
    }

    /**
     * 得到Session
     * @return
     */
    private static HttpSession getSession(){
        return getRequest().getSession();

    }

    /**
     * 得到ServletContext 对象
     * @return
     */
    private static ServletContext getServletContext(){
        return getRequest().getServletContext();
    }


//    下面是一些常用的Api

    /**
     * 设置键值在 request 域对象中
     * @param key
     * @param value
     */
    public static void setRequestAttribute(String key,String value){
        getRequest().setAttribute(key,value);
    }

    /**
     * 从request 域对象中获取键值
     * @param key
     * @param <T>
     * @return
     */
//    这个注解的意思 抑制指定编译器的警告

    @SuppressWarnings("unchecked")
    public static <T> T getRequestAttribute(String key){
        return (T) getRequest().getAttribute(key);
    }

    /**
     * 删除request 域对象中的键值
     * @param key
     */
    public static void remoteRequestAttribute(String key){
        getRequest().removeAttribute(key);
    }

    /**
     * 发送重定向相应
     * @param location  重定向的位置
     */
    public static void sendRedirect(String location){
        try {
//            当前的类路径加上指定的路径
            getResponse().sendRedirect(getRequest().getContextPath() + location);
        }catch (Exception e){
            LOGGER.error("redirect failure",e);
        }
    }

    /**
     * 就属性设置到session中
     * @param key
     * @param value
     */
    public static void setSessionAttribute(String key,String value){
       getSession().setAttribute(key,value);
    }

    /**
     * 从session 中获取值
     * @param key
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T getSessionAttribute(String key){
        return (T)getRequest().getSession().getAttribute(key);
    }

    /**
     * 从session 中删除指定的键值对
     * @param key
     */
    public static void removeSessionAttribute(String key){
       getRequest().getSession().removeAttribute(key);
    }

    /**
     * 使Session 失效
     */
    public static void invalidateSession(){
        getRequest().getSession().invalidate();
    }







}
