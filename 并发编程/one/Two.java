package com.并发编程.one;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Two {
    public static void main(String[] args) throws Exception {
//        两阶段终止模式
//
////        监控线程
//        Thread t1 = new Thread(() -> {
//            while (true){
//                boolean bool = Thread.currentThread().isInterrupted();
//                if (bool){
//                    log.debug("{}","被打断，进行料理后事操作");
//                    break;
//                }
//                try {
//                    Thread.sleep(2000);
//                    log.debug("{}","记录监控状况");
//                } catch (InterruptedException e) {
////                    这里是在sleep期间被打断的，打断标记会被置为false，所以需要重新设置打断标记
//                    System.out.println("监控线程运行期间被打断，重新设置打断标记");
//                    Thread.currentThread().interrupt();
//                }
//            }
//        }, "t1");
//
//        t1.start();
//        Thread.sleep(5000);
//        log.debug("打断监控线程");
//        t1.interrupt();

//        守护线程
//        Thread t1 = new Thread(() -> {
//                log.debug("{}","守护线程运行");
//                try {
//                    Thread.sleep(2000);
//                    log.debug("{}","守护线程运行结束");
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }, "t1");
////  设置守护线程
//        t1.setDaemon(true);
//        t1.start();
//        Thread.sleep(1000);
//        log.debug("{}","main end");

//        统筹规划
//        long star = System.currentTimeMillis();
//        Thread t1 = new Thread(() -> {
//            log.debug("{}","洗水壶1分钟");
//            try {
//                Thread.sleep(1000);
//                log.debug("{}","烧开水15分钟");
//                Thread.sleep(1500);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }, "t1");
//
//        Thread t2 = new Thread(() -> {
//            log.debug("{}","洗茶壶，拿茶叶，洗茶杯 5分钟");
//            try {
//                Thread.sleep(500);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }, "t2");
//
//        t1.start();
//        t2.start();
//        t1.join();
//        t2.join();
//        log.debug("{}","准备工作做完开始沏茶,泡茶完毕");
//        long end = System.currentTimeMillis();
//        System.out.println("一共进行了： " + (end - star));


    }
}
