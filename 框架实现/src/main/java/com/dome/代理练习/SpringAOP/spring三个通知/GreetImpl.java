package com.dome.代理练习.SpringAOP.spring三个通知;

public class GreetImpl implements Greet{
    @Override
    public void say() {
        System.out.println("greet");
    }
}
