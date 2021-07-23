package com.dome.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;


/**
 * 切面代理类 ——
 *  这里使用了一种设计模式 ：  模板方法
 */
public abstract class AspectProxy implements Proxy{
    private static final Logger logget= LoggerFactory.getLogger(AspectProxy.class);

    @Override
    public Object doProxy(ProxyChain proxyChain) throws Throwable {
        Object result = null;

        Class<?> cls = proxyChain.getTargetClass();
        Method  method = proxyChain.getTargetMethod();
        Object[] params = proxyChain.getMethodParams();

        begin();
        try {
            if (intercept(cls,method,params)){
                before(cls,method,params);
                result = proxyChain.doProxyChain();
                after(cls,method,params,result);
            }else{
                result = proxyChain.doProxyChain();
            }
        }catch (Exception e){
            logget.error("proxy failure",e);
            error(cls,method,params,e);
            throw e;
        }finally {
            end();
        }
        return result;
    }

//    下面是一系列的钩子方法  —— 可以更具具体的情况在子类中自己实现
    public  void end(){

    }

    public  void error(Class<?> cls, Method method, Object[] params, Exception e){

    }

    public  void after(Class<?> cls, Method method, Object[] params, Object result){

    }

    public  void before(Class<?> cls, Method method, Object[] params){}


    public boolean intercept(Class<?> cls, Method method, Object[] params) {
        return true;
    }

    public void begin() {
    }
}
