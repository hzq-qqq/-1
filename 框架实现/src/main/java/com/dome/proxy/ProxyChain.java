package com.dome.proxy;

import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.List;

/**
 * 代理执行链
 *   —— 这里创建的是代理对象的执行链
 */
public class ProxyChain {
    private final Class<?> targetClass;
    private final Object targetObject;
    private final Method targetMethod;
    private final MethodProxy methodProxy;
    private final Object[] methodParams;

    /**
     * 代理索引
     */
    private int proxyIndex= 0 ;

    /**
     * 代理列表
     */
    private List<Proxy> proxyList = new ArrayList<Proxy>();


    public ProxyChain(Class<?> targetClass, Object targetObject, Method targetMethod, MethodProxy methodProxy, Object[] methodParams, List<Proxy> proxyList) {
        this.targetClass = targetClass;
        this.targetObject = targetObject;
        this.targetMethod = targetMethod;
        this.methodProxy = methodProxy;
        this.methodParams = methodParams;
        this.proxyList = proxyList;
    }

    public Method getTargetMethod() {
        return targetMethod;
    }

    public Class<?> getTargetClass() {
        return targetClass;
    }

    public Object getTargetObject() {
        return targetObject;
    }

    public Object[] getMethodParams() {
        return methodParams;
    }

    public Object doProxyChain() throws Throwable{
        Object methodResult;
        if (proxyIndex < proxyList.size()){
//            这里反复的调用当前的方法
//            在Porxy 中调用完自己横切逻辑的方法之后 又通过提供的参数来调用doProxyChain ，进而调用下一个代理的横切逻辑
//            知道代理调被调用完，然后就 依次 返回结果
//            doProxy 表示的是做代理
             methodResult = proxyList.get(proxyIndex++).doProxy(this);
        }else{
//            这是cglb 提供的方法的代理
            methodResult = methodProxy.invokeSuper(targetObject,methodParams);
        }
        return methodResult;
    }
}
