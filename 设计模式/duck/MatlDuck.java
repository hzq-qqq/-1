package com.handFirst.duck;

import com.handFirst.duck.flyBehavior.FlyaWithWay;
import com.handFirst.duck.quackBehavior.QuackWithGaGa;

public class MatlDuck  extends Duck{

    MatlDuck(){
        flyBehavior = new FlyaWithWay();
        quackbehavior = new QuackWithGaGa();
    }
    @Override
    public void performanceFly() {

        flyBehavior.fly();
    }

    @Override
    public void performanvdQuack() {

        quackbehavior.quack();
    }

    @Override
    public void swim() {

    }

    @Override
    public void display() {

    }
}
