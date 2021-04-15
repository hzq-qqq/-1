package com.handFirst.命令模式.main;

import com.handFirst.命令模式.command.impl.CommendSim;
import com.handFirst.命令模式.controller.CommendInput;
import com.handFirst.命令模式.light.impl.Alight;

public class Main {
    public static void main(String[] args) {
        CommendSim lightOn = new CommendSim(new Alight());

//        这里请求者知道的只有 创建一个请求然后，然后讲请求发出，但是不知道亲求具体是有由谁来实现的
//        这里的LightOn 是他亲求的东西，实现了请求和接受的解耦
        CommendInput commendInput = new CommendInput();
        commendInput.setCommand(lightOn);
        commendInput.buttonWasPressed();
    }
}
