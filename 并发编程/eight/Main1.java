package com.并发编程.eight;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main1 {
    static volatile boolean run  = true;
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            while (run){

            }
            log.debug("end");
        }, "t1");
        t1.start();

        run = false;
    }
}
