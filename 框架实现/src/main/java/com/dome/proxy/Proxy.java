package com.dome.proxy;

/**
 * 代理接口  —— 这里会提供横切的逻辑
 */
public interface Proxy {

    /**
     * 执行链式代理 —— 就是将多个代理 以链子的方式 连接起来 依次执行代理操作
     *
     * @param proxyChain
     * @return
     * @throws Throwable
     */
//    返回结果是一个 代理类对象  —— 代理执行链
    Object doProxy(ProxyChain proxyChain) throws Throwable;
}
