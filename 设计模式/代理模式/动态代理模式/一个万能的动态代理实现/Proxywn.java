package com.handFirst.代理模式.动态代理模式.一个万能的动态代理实现;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 *
 * 自动用这个类生成代理类
 */
public class Proxywn implements InvocationHandler {
    private Object target;

    /**
     * 处理代理类并返回结果
     * @param proxy
     * @param method
     * @param args
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("代理执行了");
        return method.invoke(target, args);
    }

    public Object getProixy(){
        return Proxy.newProxyInstance(
                this.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                this);
    }

    public void setTarget(Object target) {
        this.target = target;
    }
}


