package com.设计模式.监控报警;

import com.设计模式.监控报警.handler.Handler;

public class Alert {
//    tps 的最大的请求的数量
    private final static int MAX_TPS = 10;
//    发生错误接口请求的最大数量
    private final static int MAX_ERRRORR = 100;


    private AlertRule alertRule;
    private Notification notification;
//    将所有的检查工作封装在不同的handler中
    private Handler[] myHandlers;

    public Alert() {
    }

    public Alert(AlertRule alertRule, Notification notification, Handler[] myHandlers) {
        this.alertRule = alertRule;
        this.notification = notification;
        this.myHandlers = myHandlers;
    }

    //    用一个单独的类来封装这些参数, 调用所有的handler 来分别处理
    public void check(CheckObj checkObj){
        for (Handler handler : myHandlers) {
            handler.check(checkObj);
        }
    }

}
