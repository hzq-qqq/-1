package com.并发编程.five;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Five {
    private static Object obj = new Object();
    public static void main(String[] args) throws InterruptedException {

//        注意这里必须要获取到锁之后才可以进行wait（），同理必须要获取对象之后才可以唤醒其他wait的线程
//        获取锁的线程，如果执行wait方法之后，就会释放锁，然后进入休息室，休息室里面可以有多个等地线程
//        所以在唤醒程序的时候，可以一次唤醒多个wait中线程
          new Thread(()->{
              synchronized (obj){
                  log.debug("{}","进入房间");
                  log.debug("{}","进入休息室");
                  try {
//                     未加参数的时候，默认是无线的等待，调用的是本地的方法，来wait操作系统的wait（）；
//                      可以设置等待之间，那就当等待时间到了之后，就可以继续的执行下去
//                      如果在限时等待的时间内，如果被其他线程唤醒啊，那么就会立即被唤醒进入就绪状态，不会将剩余的时间等下去
                      obj.wait();
                      log.debug("{}","被唤醒");
                  } catch (InterruptedException e) {
                      e.printStackTrace();
                  }
              }
          }).start();

    }
}
