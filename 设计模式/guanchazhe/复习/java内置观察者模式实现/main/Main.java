package com.handFirst.guanchazhe.复习.java内置观察者模式实现.main;

import com.handFirst.guanchazhe.复习.java内置观察者模式实现.Btz.Btz1;
import com.handFirst.guanchazhe.复习.java内置观察者模式实现.tz.Tz1;

public class Main {
    public static void main(String[] args) {
        Tz1 tz1 = new Tz1();

        Btz1 btz1 = new Btz1(tz1);

         tz1.setS("aaaaaaaa");
         tz1.setS("bbbbbbb");

    }
}
