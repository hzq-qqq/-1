package com.并发编程.seventh.基于AQS实现自定义同步器;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

/**
 * semaphore的用法
 */
@Slf4j
public class Main3 {
    public static void main(String[] args) throws InterruptedException {
        Semaphore semaphore = new Semaphore(0);
        new Thread(()->{
            try {
                log.debug("running .... 1");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            semaphore.release();
        }).start();

        new Thread(()->{
            try {
                log.debug("running .... 2");
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            semaphore.release();
        }).start();
        semaphore.acquire(2);
        log.debug("running main");

    }
}
