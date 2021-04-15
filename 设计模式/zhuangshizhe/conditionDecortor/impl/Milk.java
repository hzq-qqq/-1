package com.handFirst.zhuangshizhe.conditionDecortor.impl;

import com.handFirst.zhuangshizhe.becerage.Beverage;
import com.handFirst.zhuangshizhe.conditionDecortor.ConditionDectorator;

public class Milk extends ConditionDectorator {
     Beverage beverage;

    public Milk(Beverage beverage) {
        this.beverage = beverage;
    }

    @Override
    public int cost() {
        return 20 + beverage.cost();
    }

    @Override
    public String getDestribe() {
        return beverage.getDestribe() + "Milk";
    }
}
