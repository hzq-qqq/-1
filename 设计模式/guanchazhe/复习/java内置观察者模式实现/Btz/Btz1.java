package com.handFirst.guanchazhe.复习.java内置观察者模式实现.Btz;


import com.handFirst.guanchazhe.复习.java内置观察者模式实现.tz.Tz1;

import java.util.Observable;
import java.util.Observer;

public class Btz1 implements Observer {
    private String status;
    Observable observable;

    public Btz1(Observable observable){
        this.observable = observable;
        observable.addObserver(this);
    }
    @Override
    public void update(Observable o, Object arg) {
             if (o instanceof Tz1){
                 this.status = ((Tz1) o).getStatus();
             }
        System.out.println(status);
    }
}
