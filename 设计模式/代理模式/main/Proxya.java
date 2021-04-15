package com.handFirst.代理模式.main;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class Proxya implements InvocationHandler {
    private Object target;

    public Object getTarget() {
        return target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        log(method.getName());
        return method.invoke(target, args);
    }
    public void log(String msg){
        System.out.println("方法" + msg + "执行了");
    }
}
