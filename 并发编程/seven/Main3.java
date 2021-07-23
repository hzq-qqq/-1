package com.并发编程.seven;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class Main3 {
    //    锁超时机制，来自实现自己打断机制类似与waitTime（）；
//    避免锁一直等待
    static ReentrantLock lock = new ReentrantLock();
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            try {
                log.debug("尝试获取锁");
//                等待一段时间获取锁,避免无限制的等待下去
                if (!lock.tryLock(2, TimeUnit.SECONDS)){
                       log.debug("获取锁失败");
                       return;
                }
                log.debug("t1获取到锁");
            }catch (Exception e){
                log.debug("获取不到锁");
                return;
            } finally {
                lock.unlock();
            }
        }, "t1");
        t1.start();

        lock.lock();
        log.debug("main 获取到锁");
        Thread.sleep(1000);
        lock.unlock();
    }
}
