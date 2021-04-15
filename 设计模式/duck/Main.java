package com.handFirst.duck;

import com.handFirst.duck.flyBehavior.FlyWithNoWay;
import com.handFirst.duck.quackBehavior.QuackWithHaHa;

public class Main {
    public static void main(String[] args) {

        MatlDuck matlDuck = new MatlDuck();
        matlDuck.performanceFly();
        matlDuck.performanvdQuack();

        matlDuck.setFlyBehavior(new FlyWithNoWay());
        matlDuck.setQuackbehavior(new QuackWithHaHa());

        System.out.println("===================");
        matlDuck.performanceFly();
        matlDuck.performanvdQuack();
    }
}
