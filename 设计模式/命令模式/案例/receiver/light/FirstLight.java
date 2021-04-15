package com.handFirst.命令模式.案例.receiver.light;

public class FirstLight implements Light {
    @Override
    public void on() {
        System.out.println("FirstLight excute on !");
    }

    @Override
    public void off() {
        System.out.println("FirstLight excute off !");
    }
}
