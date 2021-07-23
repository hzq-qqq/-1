package com.dome.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.List;

/**
 * 用于创建一个代理类 —— 输入  被代理类  一组接口
 *                    输出：  代理对象
 *
 */
public class ProxyManager {

    /**
     * 创建 代理对象的执行连
     *    —— 重点注意：这里只有被代理的方法被执行时，才会触发 intercept（） 方法的执行, 调用执行连 之心各个代理的逻辑
     * @param targetClass
     * @param proxyList
     * @param <T>
     * @return
     */
    public static <T> T createProxy(final Class<?> targetClass, final List<Proxy> proxyList){
//        使用cglib 代理类代理  —— 拦截器 创建代理类
       return (T) Enhancer.create(targetClass, new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
//                拦截代理类方法 —— 调用执行链 来执行所有的拦截的逻辑
                return new ProxyChain(targetClass,o,method,methodProxy,objects,proxyList).doProxyChain();
            }
        });
    };
}
