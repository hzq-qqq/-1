package com.dome.代理练习.SpringAOP.抛出增强;

import com.dome.代理练习.SpringAOP.spring三个通知.Greet;
import org.springframework.aop.framework.ProxyFactory;

public class Main {
    public static void main(String[] args) {
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setTarget(new GreetImpl());
        proxyFactory.addAdvice(new GreetThrowAdvice());

        Greet proxy = (Greet) proxyFactory.getProxy();
        proxy.say();
    }
}
