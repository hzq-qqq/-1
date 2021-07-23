package com.并发编程.eight;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main2 {
    static volatile boolean flag = false;
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            while (true) {
                log.debug("执行");
                if (flag) {
                    log.debug("料理后事结束循环");
                    break;
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    log.debug("被打断，准备结束运行");
                }
            }
        }, "t1");

        t1.start();
        Thread.sleep(10);
        flag = true;
        t1.interrupt();
    }
}
