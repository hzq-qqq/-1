package com.并发编程.seven.同步运行控制;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

// 使用park/unpark 处理
@Slf4j
public class Main8 {
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
//            这里会导致阻塞
            LockSupport.park();
            log.debug("1");
        }, "t1");

        Thread t2 = new Thread(() -> {
            log.debug("2");
            LockSupport.unpark(t1);
        }, "t2");
        t1.start();
        t2.start();
    }
}
