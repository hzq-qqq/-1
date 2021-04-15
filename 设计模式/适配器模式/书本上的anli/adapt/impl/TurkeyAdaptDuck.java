package com.handFirst.适配器模式.书本上的anli.adapt.impl;

import com.handFirst.适配器模式.书本上的anli.Trukey.Trukey;
import com.handFirst.适配器模式.书本上的anli.duck.Duck;


public class TurkeyAdaptDuck  implements Duck {
    /**
     * 这里的一个适配者只包装了一个是适配者,注意针对接口进行适配,好处是同时可以适配多个该接口的子类
     * 只要是实现了Trukey 接口的任何适配类，都可以适配Duck类
     */
      Trukey firstTurkey;

    public TurkeyAdaptDuck(Trukey firstTurkey){
        this.firstTurkey = firstTurkey;
    }
    @Override
    public void quack() {
        firstTurkey.quack();
    }

    @Override
    public void fly() {
    firstTurkey.fly();
    }
}
