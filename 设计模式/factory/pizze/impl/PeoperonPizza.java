package com.handFirst.factory.pizze.impl;

import com.handFirst.factory.pizze.Pizza;

public class PeoperonPizza extends Pizza {
    @Override
    public void prepare() {
        System.out.println("PeoperonPizza prepare");
    }

    @Override
    public void bake() {
        System.out.println("PeoperonPizza bake");
    }

    @Override
    public void cut() {
        System.out.println("PeoperonPizza cut");
    }

    @Override
    public void box() {
        System.out.println("PeoperonPizza box");
    }
}
