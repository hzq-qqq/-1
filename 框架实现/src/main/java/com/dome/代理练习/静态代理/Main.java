package com.dome.代理练习.静态代理;

public class Main {
    public static void main(String[] args) {
        HelloProxy helloProxy = new HelloProxy();
        helloProxy.say("zhangsan");
    }
}
