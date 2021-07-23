package com.dome.代理练习.jdk动态代理;

import com.dome.代理练习.静态代理.Hello;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 动态代理
 *   通过代理接口中的方法来实现代理的一种方式
 */
public class DynamicProxy implements InvocationHandler {
//    方法代理的对象，这里使用的事Object 表示可以代理任何对象
    private Object target;

    public DynamicProxy(Object target) {
        this.target = target;
    }

    /**
     * 本质就是通过反射实现
     *   通过反射拿到所有被代理的方法  —— 这里使用的事jdk 动态代理，所以要求被代理类实现同一个接口，然后通过
     *   反射拿到接口中的方法，然后来执行
     *
     *
     *   所以这里被代理的类 都需要实现一个接口，代理接口中的方法
     * @param o
     * @param method
     * @param objects
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
//        执行方法，返回放方法的执行结果，将被代理对象放入
        before();
        Object result = method.invoke(target,objects);
        after();
        return result;
    }

    public  Hello getProxy() {
        return  (Hello) Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);

    }

    private void after() {
        System.out.println("after");
    }

    private void before() {
        System.out.println("before");
    }
}
