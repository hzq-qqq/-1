package com.并发编程.one;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

@Slf4j
public class One {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        Thread t2 = new Thread(() -> {
            while (true) {
                boolean bool = Thread.currentThread().isInterrupted();
                if (bool){
                    log.debug("{}","被打断了，停止运行");
                    break;
                }
            }
            log.debug("{}","被打断了，停止运行");
        }, "t1");
         t2.start();
        log.debug("{}","main running ");
        Thread.sleep(2000);
        log.debug("{}","打断t2线程的运行");
        t2.interrupt();
    }
}
