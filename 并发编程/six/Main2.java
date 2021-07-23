package com.并发编程.six;

import lombok.extern.slf4j.Slf4j;

public class Main2 {
    public static void main(String[] args) {
        BigRoom bigRoom = new BigRoom();
        new Thread(()->{
            try {
                bigRoom.sleep();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(()->{
                bigRoom.study();
        }).start();
    }
}
