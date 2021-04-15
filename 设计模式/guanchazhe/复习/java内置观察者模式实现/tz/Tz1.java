package com.handFirst.guanchazhe.复习.java内置观察者模式实现.tz;

import java.util.Observable;

public class Tz1 extends Observable {
      private String status;

      public void setS(String status){
          setChanged();
          this.status = status;
          notifyObservers();
      }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
