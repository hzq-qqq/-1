package com.handFirst.guanchazhe.复习.通知.impl;

import com.handFirst.guanchazhe.复习.被通知.Btz;
import com.handFirst.guanchazhe.复习.通知.Tz;

import java.util.ArrayList;

public class Tz1 implements Tz {
      private String status;
      static ArrayList<Btz> list = new ArrayList<>();

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public void register(Btz btz) {
         list.add(btz);
    }

    @Override
    public void remove(Btz btz) {
        list.remove(btz);
    }

    @Override
    public void notifyall() {
        for (Btz btz : list) {
            btz.update(this.status);
        }
    }

    public void set(String newsTatus){
        this.status = newsTatus;
        notifyall();
    }

    public static ArrayList<Btz> getList() {
        return list;
    }
}
