package com.handFirst.guanchazhe.Display;

import com.handFirst.guanchazhe.interf.DisplayElement;
import com.handFirst.guanchazhe.interf.Observe;
import com.handFirst.guanchazhe.interf.Subject;

public class CurrentContiditionDisplay implements DisplayElement, Observe {

    private String tem;

    private String hum;

    private String pre;
    /**
     * data 对象 用于 注册 当前的观察者
     */
    private Subject data;

    public CurrentContiditionDisplay(Subject data) {
        this.data = data;
        data.register(this);
    }

    public String getTem() {
        return tem;
    }

    public void setTem(String tem) {
        this.tem = tem;
    }

    public String getHum() {
        return hum;
    }

    public void setHum(String hum) {
        this.hum = hum;
    }

    public String getPre() {
        return pre;
    }

    public void setPre(String pre) {
        this.pre = pre;
    }

    @Override
    public String toString() {
        return "CurrentContiditionDisplay{" +
                "tem='" + tem + '\'' +
                ", hum='" + hum + '\'' +
                ", pre='" + pre + '\'' +
                '}';
    }

    @Override
    public void display() {
        System.out.println(toString());
    }

    @Override
    public void update(String tem , String hum , String pre) {
         this.tem = tem;
         this.hum = hum;
         this.pre = pre;
        System.out.println("update finish");
    }

    public void release(){
        data.release(this);
    }
}
