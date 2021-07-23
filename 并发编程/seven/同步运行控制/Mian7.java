package com.并发编程.seven.同步运行控制;

import lombok.extern.slf4j.Slf4j;

//使用 wait（）/ notify（）处理
@Slf4j
public class Mian7 {
    static final Object lock = new Object();
//    表示t2 是否运行过
    static boolean t2run = false;
    public static void main(String[] args) {
//        同步模式——控制线程的运行次序   先答应2 后打印1 —— 要求
        Thread t1 = new Thread(() -> {
            synchronized (lock){
                while (!t2run) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.debug("1");
            }
        }, "t1");

        Thread t2 = new Thread(() -> {
            synchronized (lock) {
                log.debug("2");
                t2run = true;
                lock.notify();
            }
        }, "t2");

        t1.start();
        t2.start();
    }
}
