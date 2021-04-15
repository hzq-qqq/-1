package com.handFirst.zhuangshizhe.becerage.impl;

import com.handFirst.zhuangshizhe.becerage.Beverage;
import com.handFirst.zhuangshizhe.becerage.size.Size;

public class HouseBlend extends Beverage {

    Size size;

    public HouseBlend(Size size){
        this.size = size;
        destribe = "HouseBlend";
    }
    @Override
    public int cost() {
        return 10 + size.getSize();
    }

    @Override
    public String getDestribe() {
        return destribe + "size : " + size.getSize();
    }
}
