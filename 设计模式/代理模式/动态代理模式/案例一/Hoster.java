package com.handFirst.代理模式.动态代理模式.案例一;

public class Hoster implements Rent {
    @Override
    public void rent() {
        System.out.println("出租房子");
    }
}
