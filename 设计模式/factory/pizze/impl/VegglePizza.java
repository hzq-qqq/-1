package com.handFirst.factory.pizze.impl;

import com.handFirst.factory.pizze.Pizza;

public class VegglePizza extends Pizza {
    @Override
    public void prepare() {
        System.out.println("VegglePizza prepare");
    }

    @Override
    public void bake() {
        System.out.println("VegglePizza bake");
    }

    @Override
    public void cut() {
        System.out.println("VeggdlePizza cut");
    }

    @Override
    public void box() {
        System.out.println("VegglePizza box");
    }
}
