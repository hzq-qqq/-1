package com.handFirst.命令模式.案例.command.impl;

import com.handFirst.命令模式.案例.command.Command;
import com.handFirst.命令模式.案例.receiver.Stere.Stere;

public class StereOffwithCDCommand implements Command {
    Stere stere;

    public StereOffwithCDCommand(Stere stere){
        this.stere = stere;
    }
    @Override
    public void excute() {
        stere.off();
    }

    @Override
    public void updo() {
        stere.on();
        stere.setCd();
        stere.setVolume(10);
    }
}
