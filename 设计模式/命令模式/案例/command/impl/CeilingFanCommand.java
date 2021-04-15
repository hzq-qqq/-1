package com.handFirst.命令模式.案例.command.impl;

import com.handFirst.命令模式.案例.command.Command;
import com.handFirst.命令模式.案例.receiver.ceilingFan.CeilingFan;

public class CeilingFanCommand implements Command {
    CeilingFan ceilingFan;
    int preSpeed;
    public CeilingFanCommand(CeilingFan ceilingFan){
        this.ceilingFan = ceilingFan;
    }
    @Override
    public void excute() {
        preSpeed = ceilingFan.getSpeed();
        ceilingFan.on(preSpeed);
    }

    @Override
    public void updo() {
        if (preSpeed == CeilingFan.getMAX()){
            preSpeed = CeilingFan.getMED();
            ceilingFan.on(preSpeed);
        }else if (preSpeed == CeilingFan.getMED()){
            preSpeed = CeilingFan.getMIN();
            ceilingFan.on(preSpeed);
        }else if (preSpeed == CeilingFan.getMIN()){
            preSpeed = CeilingFan.getOFF();
            ceilingFan.on(preSpeed);
        }else if (preSpeed == CeilingFan.getOFF()){
            preSpeed = CeilingFan.getMAX();
            ceilingFan.on(preSpeed);
        }
    }
}
