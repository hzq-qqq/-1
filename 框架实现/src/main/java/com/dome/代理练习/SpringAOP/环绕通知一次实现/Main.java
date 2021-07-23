package com.dome.代理练习.SpringAOP.环绕通知一次实现;

import com.dome.代理练习.SpringAOP.spring三个通知.Greet;
import com.dome.代理练习.SpringAOP.spring三个通知.GreetImpl;
import org.springframework.aop.framework.ProxyFactory;

public class Main {
    public static void main(String[] args) {
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setTarget(new GreetImpl());
        proxyFactory.addAdvice(new GreetAroundAdvice());

        Greet proxy = (Greet) proxyFactory.getProxy();
        proxy.say();
    }
}
