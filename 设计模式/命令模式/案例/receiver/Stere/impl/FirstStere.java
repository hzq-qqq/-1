package com.handFirst.命令模式.案例.receiver.Stere.impl;

import com.handFirst.命令模式.案例.receiver.Stere.Stere;

public class FirstStere  implements Stere {
    @Override
    public void on() {
        System.out.println("FirstStere excute on!");
    }

    @Override
    public void off() {
        System.out.println("FirstStere excute off");
    }

    @Override
    public void setCd() {
        System.out.println("FirstStere excute setCd");
    }

    @Override
    public void setVolume(int volume) {
        System.out.println("FirstStere excute setVolume : " + volume);
    }
}
