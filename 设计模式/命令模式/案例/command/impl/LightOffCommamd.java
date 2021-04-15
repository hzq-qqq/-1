package com.handFirst.命令模式.案例.command.impl;

import com.handFirst.命令模式.案例.command.Command;
import com.handFirst.命令模式.案例.receiver.light.Light;

public class LightOffCommamd implements Command {
    Light light;
    public LightOffCommamd(Light light){
        this.light = light;
    }
    @Override
    public void excute() {
        light.off();
    }

    @Override
    public void updo() {
         light.on();
    }
}
