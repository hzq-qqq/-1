package com.handFirst.factory.复习.抽象工厂.Xm;

import com.handFirst.factory.复习.抽象工厂.factory.SpFactory;

public class XmFactory implements SpFactory {
    @Override
    public void cPhone() {
        System.out.println("制造小米手机");
    }

    @Override
    public void cErr() {
        System.out.println("制造小米耳机");
    }
}
