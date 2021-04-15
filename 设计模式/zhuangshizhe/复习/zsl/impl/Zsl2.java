package com.handFirst.zhuangshizhe.复习.zsl.impl;

import com.handFirst.zhuangshizhe.复习.jk.Jk;
import com.handFirst.zhuangshizhe.复习.zsl.Zsl;

public class Zsl2 implements Zsl {
    Jk jk;
    public Zsl2(Jk jk){
        this.jk  =jk;
    }
    @Override
    public void cost() {
        jk.cost();
        System.out.println("装饰者2执行了");
    }
}
