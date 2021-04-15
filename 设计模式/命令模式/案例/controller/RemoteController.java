package com.handFirst.命令模式.案例.controller;


import com.handFirst.命令模式.案例.command.Command;
import com.handFirst.命令模式.案例.command.impl.NoCommand;

import java.util.Arrays;

public class RemoteController {
    Command[] onCommand ;
    Command[] offCommand;
    Command cxcommand;

    public RemoteController(){
        onCommand = new Command[7];
        offCommand = new Command[7];
         Command nocommand = new NoCommand();
         cxcommand = nocommand;
        for (int i = 0; i < onCommand.length; i++) {
              onCommand[i] = nocommand;
              offCommand[i] = nocommand;
        }
    }

    public void setOnCommand(int index, Command onCommand , Command offCommand){
          this.onCommand[index] = onCommand;
           this.offCommand[index] = offCommand;
    }

    public void onButtonWasPressed(int index){
        this.onCommand[index].excute();
        this.cxcommand = onCommand[index];
    }
    public void offButtonWasPressed(int index){
        this.offCommand[index].excute();
        this.cxcommand = offCommand[index];
    }
    public void updo(){
        cxcommand.updo();
    }
   public void updo(int index){

   }
    @Override
    public String toString() {
        return "RemoteController{" +
                "onCommand=" + Arrays.toString(onCommand) +
                ", offCommand=" + Arrays.toString(offCommand) +
                '}';
    }

    public Command[] getOnCommand() {
        return onCommand;
    }

    public Command[] getOffCommand() {
        return offCommand;
    }
}
