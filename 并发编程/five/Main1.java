package com.并发编程.five;

import lombok.extern.slf4j.Slf4j;

import static java.lang.Thread.sleep;

@Slf4j
public class Main1 {
    static final Object room = new Object();
    static boolean hasCigarette = false;
    static boolean hasTakeout = false;
    public static void main(String[] args) throws Exception {
        new Thread(() -> {
//            这里的一个严重的问题是，当小南现在在sleep的时候，不会释放锁，所以就导致其他的线程就陷入block
//            最好的方式是换成wait，当烟到了立即唤醒
            synchronized (room) {
                while (!hasCigarette) {
                    log.debug("有烟没？[{}]", hasCigarette);
                    if (!hasCigarette) {
                        log.debug("没烟，先歇会！");
                        try {
                            room.wait();
                        } catch (InterruptedException e) {
//                        当别的线程调用了interent是打断当前的线程就会导致报错
                            e.printStackTrace();
                        }
                    }
                }
                log.debug("有烟没？[{}]", hasCigarette);
                if (hasCigarette) {
                    log.debug("可以开始干活了");
                }
            }
        }, "小南").start();
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                synchronized (room) {
                    log.debug("可以开始干活了");
                }
            }, "其它人").start();
        }
        sleep(1);
//        new Thread(() -> {
//            // 这里能不能加 synchronized (room)？
////            不能，因为room 对象被小男获取的，会导致烟到了但是小南已经结束等地了，么有工作
////            改进
//            synchronized (room) {
//                hasCigarette = true;
//                log.debug("烟到了噢！");
////                这里是直接一性的唤醒所以的线程，但是会导致一些错误的唤醒
//                room.notifyAll();
//            }
//
//        }, "送烟的").start();

        new Thread(() -> {
            synchronized (room) {
                while (!hasTakeout) {
                    Thread thread = Thread.currentThread();
                    log.debug("外卖送到没？[{}]", hasTakeout);
                    if (!hasTakeout) {
                        log.debug("没外卖，先歇会！");
                        try {
                            room.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                log.debug("外卖送到没？[{}]", hasTakeout);
                if (hasTakeout) {
                    log.debug("可以开始干活了");
                } else {
                    log.debug("没干成活...");
                }
            }
        }, "小女").start();
        sleep(1);
        new Thread(() -> {
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (room) {
                hasTakeout = true;
                log.debug("外卖到了噢！");
//                这里使用的是随机唤醒一个，会导致一个错误的唤醒，比如这里会错误的唤醒雄小南线程
//                第一布改进：该未换notifyAll方法，唤醒所有的wait中的线程——虽然可以唤醒到需要被唤醒的线程
//                但是还是错误的唤醒了一些不该唤醒的线程,被唤醒后，一人不能够执行正确的逻辑
//                解决方法：
//                if 该未 while
                room.notifyAll();
            }
        }, "送外卖的").start();
    }
}
