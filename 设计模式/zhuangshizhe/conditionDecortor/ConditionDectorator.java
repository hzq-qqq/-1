package com.handFirst.zhuangshizhe.conditionDecortor;

import com.handFirst.zhuangshizhe.becerage.Beverage;

/**
 * 个人理解这个类是用区分基类和装饰类
 */
public abstract class ConditionDectorator extends Beverage {

    @Override
    public abstract String getDestribe();

}
