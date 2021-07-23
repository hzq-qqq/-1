package com.并发编程.seventh.基于AQS实现自定义同步器;

import lombok.extern.slf4j.Slf4j;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.locks.Condition;

/**
 * 测试自定义的锁
 */
@Slf4j
public class Main {
    final static NonReentrantLock lock = new NonReentrantLock();
    final static Condition noFull = lock.newCondition();
    final static Condition noEmpty = lock.newCondition();
    final static Queue<String>queue = new LinkedBlockingDeque<>();
    final static int queueSize = 10;
    public static void main(String[] args) {
        Thread producer = new Thread(() -> {
           lock.lock();
               try {
                   Thread.sleep(1000);
                   while (queue.size() > queueSize) {
                       log.debug("任务队列已经满了，陷入等待");
                       noEmpty.await();
                   }
                   queue.add("ele");
                   log.debug("添加任务到任务队列中");
                   noFull.signalAll();
                   log.debug("唤醒消费者线程");
               } catch (Exception e) {
                   e.printStackTrace();
               } finally {
                   lock.unlock();
               }
        });


        Thread consumer = new Thread(()->{
               lock.lock();
                try {
                    Thread.sleep(1000);
                    while (queue.size() == 0) {
                        log.debug("任务队列为空，陷入等待");
                        noFull.await();
                    }
                    String poll = queue.poll();
                    log.debug("消费任务队列中的任务{}", poll);
                    noEmpty.signalAll();
                    log.debug("唤醒生产者线程");
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
        });

        consumer.start();
        producer.start();



    }
}
