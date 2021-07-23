package com.dome.代理练习.静态代理;

public class HelloImpl implements Hello{
    @Override
    public void say(String name) {
        System.out.println("hello " + name);

    }
}
