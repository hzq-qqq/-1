package com.设计模式.监控报警.handler;

import com.设计模式.监控报警.CheckObj;

/**
 * @author hzq
 */
public class ErrorHandler implements Handler{
    private final static int ERROR_MAX = 10;
    @Override
    public void check(CheckObj checkObj) {
        if (checkObj.getError() > ERROR_MAX){
            throw new RuntimeException("Error overed");
        }
    }
}
