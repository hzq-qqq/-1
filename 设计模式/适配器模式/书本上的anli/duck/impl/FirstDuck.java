package com.handFirst.适配器模式.书本上的anli.duck.impl;

import com.handFirst.适配器模式.书本上的anli.duck.Duck;

public class FirstDuck implements Duck {
    @Override
    public void quack() {
        System.out.println("FirstDuck quack");
    }

    @Override
    public void fly() {
        System.out.println("FirstDuck fly");
    }
}
