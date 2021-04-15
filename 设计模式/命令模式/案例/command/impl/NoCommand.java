package com.handFirst.命令模式.案例.command.impl;

import com.handFirst.命令模式.案例.command.Command;

public class NoCommand implements Command {
    @Override
    public void excute() {
        System.out.println("没有命令");
    }

    @Override
    public void updo() {
        System.out.println("没有可以撤销的命令");
    }
}
