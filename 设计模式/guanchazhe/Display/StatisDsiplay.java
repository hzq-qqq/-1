package com.handFirst.guanchazhe.Display;

import com.handFirst.guanchazhe.interf.DisplayElement;
import com.handFirst.guanchazhe.interf.Observe;

public class StatisDsiplay implements DisplayElement, Observe {
    private String tem;

    private String hum;

    private String pre;

    @Override
    public String toString() {
        return "StatisDsiplay{" +
                "tem='" + tem + '\'' +
                ", hum='" + hum + '\'' +
                ", pre='" + pre + '\'' +
                '}';
    }

    @Override
    public void display() {

    }

    @Override
    public void update(String tem , String hum , String pre) {
        this.tem = tem;
        this.hum = hum;
        this.pre = pre;
        System.out.println("update finish");
    }
}
