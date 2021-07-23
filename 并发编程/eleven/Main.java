package com.并发编程.eleven;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;

/**
 * 测试三种不同的方式来实现累加操作
 * 1.加锁 synchronized
 * 2.无锁 AtomicInteger
 * 3.LongAdder
 */
public class Main {
    static int count = 0;
    static  volatile AtomicInteger integer = new AtomicInteger(0);
    static LongAdder longAdder = new LongAdder();
    static int target = 1000000;

    public static void main(String[] args) {
         test1();
         test2();
         test3();


    }
    static void test3(){
        long str = System.currentTimeMillis();
        ArrayList<Thread> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add( new Thread(()->{
                for (int i1 = 0; i1 < target; i1++) {
                    longAdder.increment();
                }
            }));
        }
        list.forEach(t -> t.start());

        list.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        long end = System.currentTimeMillis();
        System.out.println("time : " + (end - str));
        System.out.println("result:  " + longAdder.sum());
    }
    static void test2(){
        long str = System.currentTimeMillis();
        ArrayList<Thread> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add( new Thread(()->{
                for (int i1 = 0; i1 < target; i1++) {
                    integer.getAndIncrement();
                }
            }));
        }
        list.forEach(t -> t.start());

        list.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        long end = System.currentTimeMillis();
        System.out.println("time : " + (end - str));
        System.out.println("result:  " + integer.get());
    }

    static void test1(){
        long str = System.currentTimeMillis();
        ArrayList<Thread> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
           list.add( new Thread(()->{
               for (int i1 = 0; i1 < target; i1++) {
                   add();
               }
           }));
        }
        list.forEach(t -> t.start());

        list.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        long end = System.currentTimeMillis();
        System.out.println("time : " + (end - str));
        System.out.println("result:  " + count);
    }

    static synchronized void add(){
        count++;
    }
}
