package com.并发编程.seven;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import static java.lang.Thread.sleep;
@Slf4j
public class Main6 {
    static boolean hasCigarette = false;
    static boolean hasTakeout = false;

//    定义条件变量
    static ReentrantLock ROOM = new ReentrantLock();
    static Condition waitCiCondition = ROOM.newCondition();
    static Condition waitTaCondition = ROOM.newCondition();
    public static void main(String[] args) throws Exception {
        new Thread(() -> {
                try {
                    ROOM.lock();
                    while (!hasCigarette) {
                        log.debug("有烟没？[{}]", hasCigarette);
                        if (!hasCigarette) {
                            log.debug("没烟，先歇会！");
                            try {
                                waitCiCondition.await();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }finally {
                    ROOM.unlock();
                }
                log.debug("有烟没？[{}]", hasCigarette);
                if (hasCigarette) {
                    log.debug("可以开始干活了");
                }
        }, "小南").start();
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                try {
                    ROOM.lock();
                    log.debug("可以开始干活了");
                }finally {
                    ROOM.unlock();
                }
            }, "其它人").start();
        }
        sleep(1);

        new Thread(() -> {
               try {
                   ROOM.lock();
                   while (!hasTakeout) {
                       Thread thread = Thread.currentThread();
                       log.debug("外卖送到没？[{}]", hasTakeout);
                       if (!hasTakeout) {
                           log.debug("没外卖，先歇会！");
                           try {
                               waitTaCondition.await();
                           } catch (InterruptedException e) {
                               e.printStackTrace();
                           }
                       }
                   }
               }finally {
                   ROOM.unlock();
               }
                log.debug("外卖送到没？[{}]", hasTakeout);
                if (hasTakeout) {
                    log.debug("可以开始干活了");
                } else {
                    log.debug("没干成活...");
                }
        }, "小女").start();
        sleep(1);
        new Thread(() -> {
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                ROOM.lock();
                hasTakeout = true;
                log.debug("外卖到了噢！");
                waitTaCondition.signal();
            }finally {
                ROOM.unlock();
            }
        }, "送外卖的").start();
    }
}
