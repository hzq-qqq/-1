package com.handFirst.zhuangshizhe.conditionDecortor.impl;

import com.handFirst.zhuangshizhe.becerage.Beverage;
import com.handFirst.zhuangshizhe.conditionDecortor.ConditionDectorator;

public class Soy extends ConditionDectorator {
    Beverage beverage;

    public Soy(Beverage beverage) {
        this.beverage = beverage;
    }

    @Override
    public int cost() {
        return 15 + beverage.cost();
    }

    @Override
    public String getDestribe() {
        return beverage.getDestribe() + "Soy";
    }
}
