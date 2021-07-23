package com.并发编程.five;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main2 {
//    多线程的设计模式——保护性暂停
//    定义：
//         一个线程要同步等地另一个线程的结果，这里可以是一个中间对象来保存最终的结果

    //    获取结果的线程
    public static void main(String[] args) {
//        线程一等待线程二的结果,注意这里不同的线程需要使用同一个锁对象
        GetResult ge = new GetResult();

        new Thread(()->{
            try {
                ge.get(2000);
//                等待结果
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(()->{
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Object obj = new Object();
            log.debug("{}","返回结果");
            try {
                ge.complate(obj);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    static class GetResult {
        private Object result;

        public Object get(long timeOut) throws InterruptedException {
            synchronized (this) {
//                设置等待的时间，参数表示最大的等待的时间
//                但是在方法中不进仅仅只有等待，还会执行其他的一些操作，这些操作所花的时间也需要进行记录
//                所以就需要设置一个passTime 来判断剩余等待的时间
                long passTime = 0;
                long str = System.currentTimeMillis();
                while (result == null) {
                    long waitTime = timeOut - passTime;
                    if (waitTime <= 0 ){
                        log.debug("{}","等待的时间已经到了，退出等待");
                        break;
                    }
                    log.debug("{}","等待返回结果");
//                    这里设置时间未 timeOut - passTime
                    this.wait(waitTime);
                    passTime = System.currentTimeMillis() - str;
                }
            }

            log.debug("{}","返回");
            return result;
        }
        public void complate(Object obj) throws InterruptedException {
            synchronized (this) {
              this.result = obj;
              this.notify();
            }
        }
    }
}
