package com.handFirst.guanchazhe.subject;

import com.handFirst.guanchazhe.interf.Observe;
import com.handFirst.guanchazhe.interf.Subject;

import java.util.ArrayList;

public class WhetherData implements Subject {
    /**
     * 观察者集合 ， 温度 ， 湿度 ， 气压
     */
    private static final ArrayList<Observe> ARRAY_LIST = new ArrayList<>();

    private String tem;

    private String hum;

    private String pre;

    public WhetherData() {
    }

    public static ArrayList<Observe> getArrayList() {
        return ARRAY_LIST;
    }

    public String getTem() {
        return tem;
    }

    public String getHum() {
        return hum;
    }

    public String getPre() {
        return pre;
    }

    /**
     * 注册观察者
     */
    @Override
    public void register(Observe obj) {
        if (obj == null){
            throw new NullPointerException();
        }
        else if (!ARRAY_LIST.contains(obj)){
            ARRAY_LIST.add(obj);
        }

    }

    /**
     * 删除观察者
     */
    @Override
    public void release(Observe obj) {
        int index = ARRAY_LIST.indexOf(obj);
        if (index >= 0){
            ARRAY_LIST.remove(index);
        }
    }

    /**
     *当天气状况改变时就调用该方法
     */
    @Override
    public void notifyObserver() {
        for (Observe observe : ARRAY_LIST) {
            observe.update( tem , hum,pre);
        }
    }

    /**
     * 改变天气情况
     */
    public void measureChange(){
        notifyObserver();
    }
    public void setMeasure(String tem , String hum , String pre){
        this.tem = tem;
        this.hum = hum;
        this.pre = pre;
        measureChange();
    }
}
