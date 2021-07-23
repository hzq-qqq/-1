package com.并发编程.seven;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class Main1 {
    static ReentrantLock lock = new ReentrantLock();
    public static void main(String[] args) {
//        ReenTrantLock
//        1.支持多个条件变量，在唤醒的时候可以针对特定的条件来唤醒特定的线程
//        2.可重入
//
        //        用法：
           /*
           1.创建ReenTrantLock 对象
           2.lock()
           3.unlock()
            */
//        可重入演示
        lock.lock();
        try{
         log.debug("into main");
         m1();
        }catch (Exception e){
           e.printStackTrace();
        }finally {
            lock.unlock();
        }

    }

    private static void m1() {
        lock.lock();
        try {
            log.debug("into m1");
            m2();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
        private static void m2() {
            lock.lock();
            try {
                log.debug("into m2");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
}
