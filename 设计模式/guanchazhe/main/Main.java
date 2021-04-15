package com.handFirst.guanchazhe.main;

import com.handFirst.guanchazhe.Display.CurrentContiditionDisplay;
import com.handFirst.guanchazhe.subject.WhetherData;

public class Main {
    public static void main(String[] args) {
        // 这里气象站 就是一个 被依赖的对象
        WhetherData data = new WhetherData();
        // 这是气象板对象，他依赖了 气象站对象，当气象站对象的状态改变时，那么就会自动的更新自己的状态
        CurrentContiditionDisplay display = new CurrentContiditionDisplay(data);

        data.setMeasure("10 " , "10" , "10");
        display.display();
        data.setMeasure("20 " , "20" , "20");
        display.display();
//        当该气象板取消订阅的后，就不在自动的更新状态
        display.release();
        System.out.println("release display");
        data.setMeasure("30 " , "30" , "30");

        display.display();
    }
}
