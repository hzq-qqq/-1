package com.并发编程.seven;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class Main2 {
    static ReentrantLock lock = new ReentrantLock();
    public static void main(String[] args) throws InterruptedException {
//        可打断演示——注意不能是用lock（）加锁，因为该方法是不可以被打断的
//        可打断机制可以用于防止死锁
        Thread t1 = new Thread(() -> {
            try {
                //               如果有线程进入阻塞队列，可以被其他线程打断等待
                log.debug("尝试获取锁");
                lock.lockInterruptibly();
                log.debug("获取到锁");
            } catch (InterruptedException e) {
                e.printStackTrace();
//                这里会获取到打断异常
                log.debug("没有获取锁，退出");
                return;
            }finally {
                lock.unlock();
            }
        }, "t1");
        t1.start();

//        lock.lock();

        Thread.sleep(1);
        t1.interrupt();
    }
}
