package com.handFirst.zhuangshizhe.conditionDecortor.impl;

import com.handFirst.zhuangshizhe.becerage.Beverage;
import com.handFirst.zhuangshizhe.conditionDecortor.ConditionDectorator;

public class Whip extends ConditionDectorator {
    Beverage beverage;

    public Whip(Beverage beverage) {
        this.beverage = beverage;
    }

    @Override
    public int cost() {
        return 13 + beverage.cost();
    }

    @Override
    public String getDestribe() {
        return beverage.getDestribe() + "Whip";
    }
}
