package com.handFirst.zhuangshizhe.main;

import com.handFirst.zhuangshizhe.becerage.Beverage;
import com.handFirst.zhuangshizhe.becerage.impl.HouseBlend;
import com.handFirst.zhuangshizhe.becerage.size.impl.MinSize;
import com.handFirst.zhuangshizhe.conditionDecortor.impl.Milk;
import com.handFirst.zhuangshizhe.conditionDecortor.impl.Soy;

public class Main {
    public static void main(String[] args) {
       //Beverage 是一个基类，被装饰
        Beverage houseBlend = new HouseBlend(new MinSize());
        // 下面的是装饰类，用于装饰基类
        houseBlend = new Milk(houseBlend);
        houseBlend = new Milk(houseBlend);
        houseBlend = new Soy(houseBlend);

        System.out.println(houseBlend.getDestribe());
        System.out.println(houseBlend.cost());


    }
}
