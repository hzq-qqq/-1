package com.handFirst.factory.pizze.impl.chess;

import com.handFirst.factory.pizze.Pizza;

public class CheesePizza1 extends Pizza {
    @Override
    public void prepare() {
        System.out.println("CheesePizza1 prepare");
    }

    @Override
    public void bake() {
        System.out.println("CheesePizza1 bake");
    }

    @Override
    public void cut() {
        System.out.println("CheesePizza1 cut");
    }

    @Override
    public void box() {
        System.out.println("CheesePizza1 box");
    }
}
