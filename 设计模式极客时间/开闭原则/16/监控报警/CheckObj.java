package com.设计模式.监控报警;

public class CheckObj {
    private int tps;
    private int error;
    private int timeCount;

    public CheckObj() {
    }

    public CheckObj(int tps, int error) {
        this.tps = tps;
        this.error = error;
    }

    public CheckObj(int tps, int error, int timeCount) {
        this.tps = tps;
        this.error = error;
        this.timeCount = timeCount;
    }

    public int getTps() {
        return tps;
    }

    public int getError() {
        return error;
    }

    public int getTimeCount() {
        return timeCount;
    }
}
