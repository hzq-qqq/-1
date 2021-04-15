package com.handFirst.命令模式.案例.receiver.ceilingFan;

public class CeilingFan {
     static final int MAX = 3;
     static final int MED = 2;
     static final int MIN = 1;
     static final int OFF = 0;

    private int speed;
    private String location;

    public CeilingFan(String location) {
        this.location = location;
        speed = OFF;
    }

    public void max(){
        speed = MAX;
    }
    public void med(){
        speed = MED;
    }
    public void min(){
        speed = MIN;
    }
    public void off(){
        speed = OFF;
    }

    public int getSpeed() {
        return speed;
    }

    public void on(int size){
        System.out.println("风扇开始转动，速度为 ： " + size);
    }

    public static int getMAX() {
        return MAX;
    }

    public static int getMED() {
        return MED;
    }

    public static int getMIN() {
        return MIN;
    }

    public static int getOFF() {
        return OFF;
    }
}
