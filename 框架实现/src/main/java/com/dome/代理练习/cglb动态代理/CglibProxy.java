package com.dome.代理练习.cglb动态代理;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * cglib 动态地理 ，通过代理父类中的方法，来实现代理
 *   所以着就要求所有被代理的类都要 继承一个类
 */
public class CglibProxy implements MethodInterceptor {

    private static CglibProxy instacne = new CglibProxy();

    public CglibProxy() {
    }

    public static CglibProxy getInstacne() {
        return instacne;
    }

    /**
     * 获得被代理的对象
     * @param cls
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T  getProxy(Class<T> cls){
        return (T) Enhancer.create(cls,instacne);
    }
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        before();
//        可以看到这里这里调用的super
        Object result = methodProxy.invokeSuper(o, objects);
        after();
        return result;
    }

    private void after() {
        System.out.println("after");
    }

    private void before() {
        System.out.println("before");
    }
}
