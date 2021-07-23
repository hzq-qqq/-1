package com.并发编程.third;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * 线程池
 */
@Slf4j
public class Main1 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

//        ExecutorService pool = Executors.newFixedThreadPool(2);
//        pool.execute(()->{
//            log.debug("1");
//        });
//        pool.execute(()->{
//            log.debug("2");
//        });
//        pool.execute(()->{
//            log.debug("3");
//        });
//        pool.execute(()->{
//            log.debug("4");
//        });

//        ExecutorService pool = Executors.newSingleThreadExecutor();
//        pool.execute(()->{
//            int a = 1 / 0;
//        });
//        pool.execute(()->{
//            log.debug("2");
//        });
//        pool.execute(()->{
//            log.debug("3");
//        });

        ExecutorService pool = Executors.newFixedThreadPool(2);
        ExecutorService cookPool = Executors.newFixedThreadPool(2);
//        Future<String> future = pool.submit(() -> {
//
//            log.debug("1");
//            return "ok";
//        });
//        String s = future.get();
//        System.out.println(s);

        /**
         * 线程不足到导致线程获取不到资源执行不下去
         */
        pool.execute(()->{
            log.debug("处理点餐");
            Future<String> future = cookPool.submit(() -> {
                return "做菜";
            });
            try {
                log.debug("{}",future.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });
        pool.execute(()->{
            log.debug("处理点餐");
            Future<String> future = cookPool.submit(() -> {
                return "做菜";
            });
            try {
                log.debug("{}",future.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });

        /**
         * 解决方法
         *    不同的任务类型使用不同的线程池
         */




    }
}
