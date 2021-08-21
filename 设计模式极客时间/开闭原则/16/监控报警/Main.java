package com.设计模式.监控报警;

import com.设计模式.监控报警.handler.ErrorHandler;
import com.设计模式.监控报警.handler.Handler;
import com.设计模式.监控报警.handler.TimeCountHandler;
import com.设计模式.监控报警.handler.TpsHandler;


public class Main {

    public static void main(String[] args) {
        int tps = 9;
        int error = 9;
        int timeCount = 9;
        CheckObj checkObj = new CheckObj(tps,error,timeCount);
        AlertRule alertRule = new AlertRule();
        Notification notification = new Notification();
        Handler[] handlers = new Handler[3];
        handlers[0] = new TpsHandler();
        handlers[1] =  new ErrorHandler();
        handlers[2] =  new TimeCountHandler();
        Alert alert = new Alert(alertRule,notification,handlers);
        alert.check(checkObj);
    }
}
