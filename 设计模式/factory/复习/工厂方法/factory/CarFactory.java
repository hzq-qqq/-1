package com.handFirst.factory.复习.工厂方法.factory;

import com.handFirst.factory.复习.简单工厂.car.Car;
import com.handFirst.factory.复习.简单工厂.car.impl.Bm;

public class CarFactory implements FactoryC{

       public static Car getCar(){
           return new Bm();
       }
}
