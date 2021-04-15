package com.handFirst.命令模式.案例.command.impl;

import com.handFirst.命令模式.案例.command.Command;

public class MorcCommand implements Command {
    Command[] commands;

    public MorcCommand(Command[] commands){
        this.commands = commands;
    }
    @Override
    public void excute() {
        for (Command command : commands) {
            command.excute();
        }
    }

    @Override
    public void updo() {
        for (Command command : commands) {
            command.updo();
        }
    }
}
