package com.handFirst.factory.复习.简单工厂.main;

import com.handFirst.factory.复习.简单工厂.car.Car;
import com.handFirst.factory.复习.简单工厂.factory.CarFactory;

public class Main {
    public static void main(String[] args) {

        Car bm = CarFactory.getCar("bm");
        bm.show();
        Car wl = CarFactory.getCar("wl");
        wl.show();
    }
}
