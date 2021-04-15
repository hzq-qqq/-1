package com.handFirst.命令模式.案例.main;

import com.handFirst.命令模式.案例.command.Command;
import com.handFirst.命令模式.案例.command.impl.*;
import com.handFirst.命令模式.案例.controller.RemoteController;
import com.handFirst.命令模式.案例.receiver.Stere.impl.FirstStere;
import com.handFirst.命令模式.案例.receiver.ceilingFan.CeilingFan;
import com.handFirst.命令模式.案例.receiver.light.FirstLight;

public class Main {
    public static void main(String[] args) {
        RemoteController controller = new RemoteController();

        controller.setOnCommand(0,new LightOnCommamd(new FirstLight()),new LightOffCommamd(new FirstLight()));
        controller.onButtonWasPressed(0);
        controller.updo();
        System.out.println("====================================");
        controller.setOnCommand(1,new StereOnwithCDCommand(new FirstStere()),new StereOffwithCDCommand(new FirstStere()));
         controller.onButtonWasPressed(1);
         controller.updo();

        System.out.println("====================================");
        controller.setOnCommand(2,new CeilingFanCommand(new CeilingFan("living room" )),new CeilingFanCommand(new CeilingFan("living room" )));
        controller.onButtonWasPressed(2);
        controller.updo();
        controller.updo();
        controller.updo();
         controller.updo();

        System.out.println("====================================");
        System.out.println("直接传入命令组的方式，可以是实现命令的自由组合，相比直接在Morc命令中调用调用者命令集合中全部的命令更加的灵活");
        System.out.println("可以实现命令的自由组合!");
        Command[] onCommand = {new LightOnCommamd(new FirstLight()),new StereOnwithCDCommand(new FirstStere())};
        Command[] offCommand = {new LightOffCommamd(new FirstLight()),new StereOffwithCDCommand(new FirstStere())};
        controller.setOnCommand(6,new MorcCommand(onCommand),new MorcCommand(offCommand));

        controller.onButtonWasPressed(6);
        System.out.println("撤销。。。。。。。。。。。。");
        controller.updo();

    }
}
