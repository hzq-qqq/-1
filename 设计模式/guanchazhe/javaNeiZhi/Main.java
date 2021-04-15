package com.handFirst.guanchazhe.javaNeiZhi;


public class Main {
    public static void main(String[] args) {
        WeatherData data = new WeatherData();
        CurrentConditionDisplay dis = new CurrentConditionDisplay(data);

        data.setMeasures("10","10","10");
        data.setMeasures("20","10","10");
        data.deleteObserver(dis);
        data.setMeasures("30","10","10");

        dis.display();
    }
}
