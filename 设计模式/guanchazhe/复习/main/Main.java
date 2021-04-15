package com.handFirst.guanchazhe.复习.main;

import com.handFirst.guanchazhe.复习.被通知.impl.Btz1;
import com.handFirst.guanchazhe.复习.通知.impl.Tz1;

public class Main {
    public static void main(String[] args) {
        Btz1 btz1 = new Btz1();
        Tz1 tz = new Tz1();
        btz1.setTz(tz);

        tz.set("aaaaa");
        tz.set("bbbbb");
        tz.remove(btz1);
        tz.set("bbbbbc");

    }
}
