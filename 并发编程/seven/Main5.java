package com.并发编程.seven;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class Main5 {
    static ReentrantLock lock = new ReentrantLock();
    public static void main(String[] args) throws InterruptedException {
//        条件变量

//        类似与synchronized中的锁对象就是一个条件变量

//        ReentrantLock 支持多个锁——将功能又进行了细分
//           创建一个新的条件变量
        Condition condition1 = lock.newCondition();
        Condition condition2 = lock.newCondition();
        log.debug("main 获取锁");
        lock.lock();
//       调用条件进入休息室等待
        condition1.await();
        log.debug("被唤醒");

//        其他线程通知condition1中某一个等待的线程
        condition1.signalAll();
    }
}
