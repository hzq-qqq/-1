package com.handFirst.zhuangshizhe.复习.zsl.impl;

import com.handFirst.zhuangshizhe.复习.jk.Jk;
import com.handFirst.zhuangshizhe.复习.zsl.Zsl;

public class Zsl1 implements Zsl {
    Jk jk;
    public Zsl1(Jk jk){
        this.jk  =jk;
    }
    @Override
    public void cost() {
        jk.cost();
        System.out.println("装饰者1执行了");
    }
}
