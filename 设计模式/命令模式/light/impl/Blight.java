package com.handFirst.命令模式.light.impl;

import com.handFirst.命令模式.light.Light;

public class Blight implements Light {
    @Override
    public void on() {
        System.out.println("Blight on ");
    }

    @Override
    public void off() {
        System.out.println("Blight off ");
    }
}
