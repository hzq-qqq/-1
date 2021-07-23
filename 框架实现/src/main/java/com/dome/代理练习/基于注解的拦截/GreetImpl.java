package com.dome.代理练习.基于注解的拦截;

import com.dome.代理练习.SpringAOP.spring三个通知.Greet;

public class GreetImpl implements Greet {
    @Override
    public void say() {
        System.out.println("hello world");
    }

    @Tag
    public void goordMorning(){
        System.out.println("goordMorning...");
    }

    public void goordAfternoon(){
        System.out.println("goordAfternoon...");
    }
}
