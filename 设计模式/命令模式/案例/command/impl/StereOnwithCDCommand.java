package com.handFirst.命令模式.案例.command.impl;

import com.handFirst.命令模式.案例.command.Command;
import com.handFirst.命令模式.案例.receiver.Stere.Stere;

public class StereOnwithCDCommand implements Command {
    Stere stere;

    public StereOnwithCDCommand(Stere stere){
        this.stere = stere;
    }
    @Override
    public void excute() {
        stere.on();
        stere.setCd();
        stere.setVolume(10);
    }

    @Override
    public void updo() {
        stere.off();
    }
}
