package com.并发编程.eight;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

@Slf4j
public class Main {
    /**
     * 这里在构造的是时候，需要传递当前共享变量的版本号
     */
    static AtomicStampedReference<String> ref = new AtomicStampedReference<>("A",0);
    public static void main(String[] args) throws InterruptedException {
        log.debug("main start...");
        // 获取值 A
        // 这个共享变量被它线程修改过？
        String prev = ref.getReference();
//        获取当前的版本号
        int stamp = ref.getStamp();
        log.debug("stamp: {}",stamp);
        other();
        Thread.sleep(1);
        // 尝试改为 C
//        ABA问题 —— 主线程不能够感知到其他线程的修改  —— AtomicReference 当变化前后的值相同时，该类对象不能够感知 共享遍历的变化
//       解决方法 ——  AtomicStampedReference —— 使用版本号的方式来判断共享遍历是否未修改


        /**
         * stamp 当前版本号
         *
         * stamp + 1  执行成功之后，将版本号加 1
         *
         * main 线程的更新失败是因为：版本号被其他线程修改了 main 中的版本号为 0  当前共享变量的最新版本是2
         */
        log.debug("change A->C {}", ref.compareAndSet(prev, "C",stamp,stamp + 1));
    }

    private static void other() throws InterruptedException {

        new Thread(() -> {
            int stamp = ref.getStamp();
            log.debug("stamp:{}",stamp);
            log.debug("change A->B {}", ref.compareAndSet(ref.getReference(), "B",stamp,stamp + 1));
        }, "t1").start();
        Thread.sleep(1);
        new Thread(() -> {
            int stamp = ref.getStamp();
            log.debug("stamp:{}",stamp);
            log.debug("change B->A {}", ref.compareAndSet(ref.getReference(), "A",stamp,stamp + 1));
        }, "t2").start();
    }
}
