package com.dome.helper;

import com.dome.annotation.Action;
import com.dome.bean.Handler;
import com.dome.bean.Request;
import com.dome.util.ArrayUtil;
import com.dome.util.CollectionUtil;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 控制器助手类 —— 用于 绑定 request 和 handler
 * @author hzq
 */
public class ControllerHelper {

    private static final Map<Request, Handler> ACTION_MAP = new HashMap<Request, Handler>();

    static {
        final Set<Class<?>> controllerClassSet = ClassHelper.getControllerClassSet();
//        controller 个数不为0
        if (CollectionUtil.isNotEmpty(controllerClassSet)){
//            遍历所有的Controller 类
            for (Class<?> aClass : controllerClassSet) {
                final Method[] methods = aClass.getDeclaredMethods();
//                遍历controller 类的所有的方法
                if (ArrayUtil.isNotEmpty(methods)){
                    for (Method method : methods) {
//                        判断方法是否标注了@Action 注解修饰
                        if (method.isAnnotationPresent(Action.class)){
//                            获取该注解
                            final Action action = method.getAnnotation(Action.class);
//                            得到注解中写入的 url
                            final String mapping = action.value();
//                            检验url 是否符合 书写规则
                            if (mapping.matches("\\w+:/\\w*")){
                                final String[] array = mapping.split(":");
                                if (ArrayUtil.isNotEmpty(array) && array.length == 2){
//                                    获取请求方式
                                    final String requestMethod = array[0];
//                                    获取请求的path
                                    final String requestPath = array[1];
//                                    封装request 对象和handler 对象
                                    final Request request = new Request(requestMethod, requestPath);
                                    final Handler handler = new Handler(aClass, method);

//                                    将映射存入Action 中
                                    ACTION_MAP.put(request,handler);

                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 根据方法名和请求方式 ， 获取 handler     例子： get:login
     * @param requestMethod  请求方式
     * @param requestPath    请求地址
     * @return
     */
    public static Handler getHandler(String requestMethod , String requestPath){
        final Request request = new Request(requestMethod, requestPath);
        return ACTION_MAP.get(request);
    }
}
