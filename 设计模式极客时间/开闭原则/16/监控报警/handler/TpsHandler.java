package com.设计模式.监控报警.handler;

import com.设计模式.监控报警.CheckObj;

public class TpsHandler implements Handler{
    private final static int TPS_MAX = 10;
    @Override
    public void check(CheckObj checkObj) {
        if (checkObj.getTps() > TPS_MAX){
            throw new RuntimeException("Tps overed");
        }
    }
}
