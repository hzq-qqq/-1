package com.并发编程.five.案例2;

import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;

@Slf4j
public class MessagQueue {
    private final LinkedList<Message> list = new LinkedList<>();
    private final Integer capcity;

    public MessagQueue(Integer capcity) {
        this.capcity = capcity;
    }

    public Message take() {
        synchronized (list) {
            while (list.isEmpty()) {
                try {
                    log.debug("队列为空，消费者线程等待");
                    list.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            log.debug("已经消费消息");
            return list.removeFirst();
        }
    }

    public void put(Message msg) {
        synchronized (list) {
             while (capcity == list.size()){
                 try {
                     log.debug("队列已满，生产者线程等待");
                     list.wait();
                 } catch (InterruptedException e) {
                     e.printStackTrace();
                 }
             }
        }
        list.add(msg);

        log.debug("已经生产消息");
    }
}
