package com.handFirst.命令模式.案例.command.impl;

import com.handFirst.命令模式.案例.command.Command;
import com.handFirst.命令模式.案例.receiver.light.Light;

public class LightOnCommamd implements Command {
    Light light;
    public LightOnCommamd(Light light){
        this.light = light;
    }
    @Override
    public void excute() {
        light.on();
    }

    @Override
    public void updo() {
        light.off();
    }
}
