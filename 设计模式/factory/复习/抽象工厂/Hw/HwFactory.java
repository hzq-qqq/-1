package com.handFirst.factory.复习.抽象工厂.Hw;

import com.handFirst.factory.复习.抽象工厂.factory.SpFactory;

public class HwFactory implements SpFactory {
    @Override
    public void cPhone() {
        System.out.println("制造华为手机");
    }

    @Override
    public void cErr() {
        System.out.println("制造华为耳机");
    }
}
