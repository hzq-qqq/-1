package com.并发编程.seventh.基于AQS实现自定义同步器;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class Main5 {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(2);
        CountDownLatch countDownLatch = new CountDownLatch(3);
        pool.submit(
                ()->{
                log.debug("begin 1...");
        countDownLatch.countDown();
        log.debug("end..... 1");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    });
        pool.submit(
                ()->{
                    log.debug("begin 2...");
                    countDownLatch.countDown();
                    log.debug("end..... 2");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
        pool.submit(
                ()->{
                    log.debug("begin 3...");
                    countDownLatch.countDown();
                    log.debug("end..... 3");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
        log.debug("main wait...");
        countDownLatch.await();
        log.debug("wair end .....");
    }
}
