package com.handFirst.guanchazhe.复习.被通知.impl;

import com.handFirst.guanchazhe.复习.被通知.Btz;
import com.handFirst.guanchazhe.复习.通知.Tz;

public class Btz1 implements Btz {
    private String status;
     Tz tz;

    public void setTz(Tz tz) {
        this.tz = tz;
        tz.register(this);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public void update(String status) {
        this.status = status;
        System.out.println("current : " + status);
    }
}
