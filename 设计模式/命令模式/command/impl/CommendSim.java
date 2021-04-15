package com.handFirst.命令模式.command.impl;

import com.handFirst.命令模式.command.Command;
import com.handFirst.命令模式.light.Light;

public class CommendSim implements Command {
    Light light;

    public CommendSim(Light light){
        this.light = light;
    }
    @Override
    public void excute() {
        light.on();
    }

    public void setLight(Light light) {
        this.light = light;
    }
}
