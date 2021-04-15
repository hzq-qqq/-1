package com.handFirst.factory.复习.工厂方法.factory;

import com.handFirst.factory.复习.工厂方法.car.impl.Hg;
import com.handFirst.factory.复习.工厂方法.car.Car;

public class HgFactory implements FactoryC{

       public static Car getCar(){

           return new Hg();
       }
}
