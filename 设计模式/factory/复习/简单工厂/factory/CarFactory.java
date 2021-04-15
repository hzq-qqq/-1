package com.handFirst.factory.复习.简单工厂.factory;

import com.handFirst.factory.复习.简单工厂.car.Car;
import com.handFirst.factory.复习.简单工厂.car.impl.Bm;
import com.handFirst.factory.复习.简单工厂.car.impl.Wl;

public class CarFactory {
    public static Car getCar(String name){
        if (name.equals("bm")){
            return new Bm();
        }
        if (name.equals("wl")){
            return new Wl();
        }
        return null;
    }
}
