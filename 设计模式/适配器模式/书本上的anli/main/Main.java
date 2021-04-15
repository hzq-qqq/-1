package com.handFirst.适配器模式.书本上的anli.main;

import com.handFirst.适配器模式.书本上的anli.Trukey.FirstTurkey;
import com.handFirst.适配器模式.书本上的anli.adapt.impl.TurkeyAdaptDuck;

public class Main {
    public static void main(String[] args) {
        FirstTurkey firstTurkey = new FirstTurkey();
        TurkeyAdaptDuck turkeyAdaptDuck = new TurkeyAdaptDuck(firstTurkey);
        firstTurkey.fly();
        firstTurkey.quack();

    }
}
