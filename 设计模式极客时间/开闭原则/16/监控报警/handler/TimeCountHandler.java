package com.设计模式.监控报警.handler;

import com.设计模式.监控报警.CheckObj;

/**
 * @author hzq
 */
public class TimeCountHandler implements Handler{
    private final static int TIME_COUNT = 10;
    @Override
    public void check(CheckObj checkObj) {
        if (checkObj.getTimeCount() > TIME_COUNT){
            throw new RuntimeException("TimeCount overed");
        }
    }
}
