package com.dome.代理练习.基于注解的拦截;

import com.dome.代理练习.SpringAOP.spring三个通知.Greet;

public class Main {
    public static void main(String[] args) {
        GreetImpl greet = new GreetImpl();

        greet.goordAfternoon();

        greet.goordMorning();
    }
}
