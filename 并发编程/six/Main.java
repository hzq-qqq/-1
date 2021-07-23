package com.并发编程.six;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

@Slf4j
public class Main {
    public static void main(String[] args) throws InterruptedException {
//        park 和 unpark  是以线程为单位的打断与唤醒 ——c 代码实现
//        注意 notify / wait 不是以线程为单为的，而是以对象为单位进行等待和唤醒
//        注意这里先执行了unpark  当线程被park 的时候 不会被打断，会继续执行
        Thread t1 = new Thread(() -> {
            log.debug("start");
                LockSupport.park();
                log.debug("end");
        });
        t1.start();
        log.debug("main star");
        Thread.sleep(1000);
        LockSupport.unpark(t1);
        log.debug("main end");
    }
}
