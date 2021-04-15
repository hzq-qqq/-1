package com.handFirst.duck;

import com.handFirst.duck.quackBehavior.QuackBehavior;
import com.handFirst.duck.flyBehavior.FlyBehavior;

public abstract class Duck {
    protected FlyBehavior flyBehavior;
    protected QuackBehavior quackbehavior;

    public abstract void performanceFly();

    public abstract void performanvdQuack();

    public abstract void swim();

    public abstract void display();

    public FlyBehavior getFlyBehavior() {
        return flyBehavior;
    }

    public void setFlyBehavior(FlyBehavior flyBehavior) {
        this.flyBehavior = flyBehavior;
    }

    public QuackBehavior getQuackbehavior() {
        return quackbehavior;
    }

    public void setQuackbehavior(QuackBehavior quackbehavior) {
        this.quackbehavior = quackbehavior;
    }
}
