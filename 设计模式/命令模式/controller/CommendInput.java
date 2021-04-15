package com.handFirst.命令模式.controller;

import com.handFirst.命令模式.command.Command;

public class CommendInput {
    Command command;

    public CommendInput(){
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    public void buttonWasPressed(){
        command.excute();
    }
}
