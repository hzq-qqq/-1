package com.dome.bean;

import java.lang.reflect.Method;

/**
 *  用于封装Action 信息
 */
public class Handler {

    /**
     * Controller 类
     */

    private final Class<?> controllerClass;

    /**
     *   请求返回相关信息
     */
    private final Method actionMethod;

    public Handler(Class<?> controllerClass, Method actionMethod) {
        this.controllerClass = controllerClass;
        this.actionMethod = actionMethod;
    }

    public Class<?> getControllerClass() {
        return controllerClass;
    }

    public Method getActionMethod() {
        return actionMethod;
    }
}
