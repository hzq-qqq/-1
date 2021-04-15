package com.handFirst.factory.pizze.impl.chess;

import com.handFirst.factory.pizze.Pizza;

public class CheesePizza2 extends Pizza {
    @Override
    public void prepare() {
        System.out.println("CheesePizza2 prepare");
    }

    @Override
    public void bake() {
        System.out.println("CheesePizza2 bake");
    }

    @Override
    public void cut() {
        System.out.println("CheesePizza2 cut");
    }

    @Override
    public void box() {
        System.out.println("CheesePizza2 box");
    }
}
