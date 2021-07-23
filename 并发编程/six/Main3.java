package com.并发编程.six;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main3 {
    public static void main(String[] args) {
        Object a = new Object();
        Object b = new Object();

        new Thread(()->{
            synchronized (a){
                log.debug("get a");
                synchronized (b){
                    log.debug("get b ");
                }
            }
        }).start();
        new Thread(()->{
            synchronized (b){
                log.debug("get b");
                synchronized (a){
                    log.debug("get a ");
                }
            }
        }).start();
    }
}
