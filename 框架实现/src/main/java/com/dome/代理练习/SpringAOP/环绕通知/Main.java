package com.dome.代理练习.SpringAOP.环绕通知;

import com.dome.代理练习.SpringAOP.spring三个通知.Greet;
import com.dome.代理练习.SpringAOP.spring三个通知.GreetImpl;
import org.springframework.aop.framework.ProxyFactory;

/**
 * 环绕通知
 */
public class Main {
    public static void main(String[] args) {
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setTarget(new GreetImpl());
        proxyFactory.addAdvice(new GreetBeforeAndAfterAdvice());

        Greet proxy = (Greet) proxyFactory.getProxy();
        proxy.say();
    }
}
