package com.handFirst.封装算法.caffer.impl;

import com.handFirst.封装算法.caffer.CaffeineBeveragge;

import java.util.Scanner;

public class Coffer extends CaffeineBeveragge {
    @Override
    protected void addCondiments() {
        System.out.println(" add sugar and milk");
    }

    @Override
    protected void brew() {
        System.out.println("brew coffer");
    }

    @Override
    public boolean hook() {
        Scanner sin = new Scanner(System.in);
        String s = sin.nextLine();
        return "y".equals(s);
    }
}
