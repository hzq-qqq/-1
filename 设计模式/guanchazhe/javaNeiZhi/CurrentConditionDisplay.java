package com.handFirst.guanchazhe.javaNeiZhi;

import com.handFirst.guanchazhe.interf.DisplayElement;

import java.util.Observable;
import java.util.Observer;

public class CurrentConditionDisplay implements Observer , DisplayElement {
    private String tem;
    private String hum;
    private String pre;
    Observable observable;

    public CurrentConditionDisplay(Observable observable){
        this.observable = observable;
        this.observable.addObserver(this);
    }
    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof WeatherData){
            this.tem = ((WeatherData) o).getTem();
            this.hum = ((WeatherData) o).getHum();
            this.pre = ((WeatherData) o).getPre();
            display();
        }
    }


    @Override
    public void display() {
        System.out.println(toString());
    }

    @Override
    public String toString() {
        return "CurrentConditionDisplay{" +
                "tem='" + tem + '\'' +
                ", hum='" + hum + '\'' +
                ", pre='" + pre + '\'' +
                '}';
    }
}
