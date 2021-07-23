package com.dome.代理练习.SpringAOP.切面;

import com.dome.代理练习.SpringAOP.spring三个通知.Greet;

public class GreeetImpl implements Greet {
    @Override
    public void say() {
        System.out.println("hello world " + " zhangsan ");
    }

    public void goodMorning(String name){
        System.out.println("goodMorning! " + name);
    }

    public void goodAfternoon(String name){
        System.out.println("good Afternoon " + name);
    }
}
