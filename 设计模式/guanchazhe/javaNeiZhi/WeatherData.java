package com.handFirst.guanchazhe.javaNeiZhi;

import java.util.Observable;

public class WeatherData extends Observable {
    private  String tem ;
    private  String hum ;
    private  String pre ;

    /**
     * 讲内容推送给每个布告板
     */
    public void measureChange(){
        setChanged();
        notifyObservers();
    }

    /**
     * 获取最新状态
     * @param tem 气温
     * @param hum 湿度
     * @param pre 气压
     */
    public void setMeasures(String tem , String hum , String pre){
        this.tem = tem;
        this.hum = hum;
        this.pre = pre;
        measureChange();
    }

    public String getPre() {
        return pre;
    }

    public String getTem() {
        return tem;
    }

    public String getHum() {
        return hum;
    }
}
