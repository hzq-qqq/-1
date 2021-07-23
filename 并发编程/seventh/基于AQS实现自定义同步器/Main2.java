package com.并发编程.seventh.基于AQS实现自定义同步器;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.StampedLock;


@Slf4j
public class Main2 {
    public static void main(String[] args) {

    }

    @Slf4j
    static class DataContainer{
        private String data;
        private StampedLock lock;

        public DataContainer() {
            data = "data";
            this.lock = new StampedLock();
        }

        public String read() throws InterruptedException {
            long stamp = lock.tryOptimisticRead();
            Thread.sleep(1000);
            log.debug("乐观读取数据");
            if (lock.validate(stamp)){
                log.debug("读取完成 {}",stamp);
                return data;
            }

//            锁升级  升级为读锁来读取数据
            try {
                 stamp = lock.readLock();
                log.debug("锁升级");
                Thread.sleep(1000 );
                return data;
            }finally {
                lock.unlock(stamp);
            }
        }
    }
}
