package com.handFirst.代理模式.动态代理模式.案例一;

public class Customer implements Rent {
    @Override
    public void rent() {
        System.out.println("租房子");
    }
}
