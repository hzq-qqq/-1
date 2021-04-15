package com.handFirst.factory.复习.工厂方法.main;

import com.handFirst.factory.复习.工厂方法.factory.CarFactory;
import com.handFirst.factory.复习.工厂方法.factory.HgFactory;
import com.handFirst.factory.复习.工厂方法.car.Car;


public class Main {
    public static void main(String[] args) {

        Car car = HgFactory.getCar();
        car.show();
    }
}
