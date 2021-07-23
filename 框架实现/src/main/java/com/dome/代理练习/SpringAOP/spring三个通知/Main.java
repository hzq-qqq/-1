package com.dome.代理练习.SpringAOP.spring三个通知;

import org.springframework.aop.framework.ProxyFactory;

public class Main {
    public static void main(String[] args) {
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setTarget(new GreetImpl());
        proxyFactory.addAdvice(new GreetBeforeAdvice());
        proxyFactory.addAdvice(new GreetAfterAdvice());
        Greet proxy = (Greet) proxyFactory.getProxy();
        proxy.say();
    }
}
