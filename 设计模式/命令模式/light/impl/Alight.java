package com.handFirst.命令模式.light.impl;

import com.handFirst.命令模式.light.Light;

public class Alight implements Light {
    @Override
    public void on() {
        System.out.println("Alight on ");
    }

    @Override
    public void off() {
        System.out.println("Alight off ");
    }
}
