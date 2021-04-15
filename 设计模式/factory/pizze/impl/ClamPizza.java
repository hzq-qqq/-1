package com.handFirst.factory.pizze.impl;

import com.handFirst.factory.pizze.Pizza;

public class ClamPizza extends Pizza {
    @Override
    public void prepare() {
        System.out.println("ClamPizza prepare");
    }

    @Override
    public void bake() {
        System.out.println("ClamPizza bake");
    }

    @Override
    public void cut() {
        System.out.println("ClamPizza cut");
    }

    @Override
    public void box() {
        System.out.println("ClamPizza box");
    }
}
