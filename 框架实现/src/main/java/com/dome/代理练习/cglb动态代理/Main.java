package com.dome.代理练习.cglb动态代理;


import com.dome.代理练习.静态代理.HelloImpl;

public class Main {
    public static void main(String[] args) {
//        这里使用单例模式来创建代理类
        HelloImpl proxy = CglibProxy.getProxy(HelloImpl.class);
        proxy.say("张三");
    }
}
