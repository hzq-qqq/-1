package com.并发编程.tenth;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class Main1 {
    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(2);
        CyclicBarrier barrier = new CyclicBarrier(2, () -> {
//            当 state 的值为 0 时执行这的代码
            log.debug("test1,test2 end....");
        });

            pool.submit(() -> {
                log.debug("running .... 1");
                try {
                    Thread.sleep(1000);
                    barrier.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            pool.submit(() -> {
                log.debug("running .... 2");
                try {
                    Thread.sleep(2000);
                    barrier.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            pool.shutdown();
        }

    @Test
    public void test2() {
    }
    public void test(){
        ExecutorService pool = Executors.newFixedThreadPool(3);
        CountDownLatch latch = new CountDownLatch(3);
        pool.submit(()->{
            log.debug("running .... 1");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.debug("end....1");
            latch.countDown();
        });
        pool.submit(()->{
            log.debug("running .... 2");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.debug("end....2");
            latch.countDown();
        });
        pool.submit(()->{
            log.debug("running .... 3");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.debug("end....3");
            latch.countDown();
        });
        pool.shutdown();
    }

}
