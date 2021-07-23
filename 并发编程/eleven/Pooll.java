package com.并发编程.eleven;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicIntegerArray;

@Slf4j
public class Pooll {
//    连接池的大小
    private final int poolSize;

//    连接池数组
   private Connection[] connections;
//    连接状态数组   0 : 空闲  1 ： 繁忙   使用原子整数数组，对数组进行保护
    private AtomicIntegerArray status;

    private Semaphore semaphore;


//    初始化
    public Pooll (int poolSize){
        this.poolSize = poolSize;
        this.connections = new Connection[poolSize];
        this.status = new AtomicIntegerArray(poolSize);
        this.semaphore = new Semaphore(poolSize);
        for (int i = 0; i < poolSize; i++) {
            connections[i] = new MockConnection("连接" + i + 1);
        }
    }

//    借出连接的方法  ——  归还连接的方法

    public Connection borrow()  {
        //        改进
        try {
            semaphore.acquire();
        }catch (Exception e){
            e.printStackTrace();
        }
            for (int i = 0; i < poolSize; i++) {
                if (status.get(i) == 0){
                    /**
                     * 返回连接对象   修改下标为 i  的元素的值
                     */
                    log.debug("{}","borrow....");
                    status.compareAndSet(i,0,1);
                    return connections[i];
                }
            }
            return null;
//            /**
//             * 没有空闲连接 —— 调用线程池的休息室进行等待
//             */
//            synchronized (this){
//                log.debug("{}","wait....");
//                try {
//                    this.wait();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
    }

//    归还连接
    public void free(Connection connection){
        /**
         * 遍历连接池对象 ——  判断 连接对象的数组中 的对象是否和 connection 是同一个对象
         */
        for (int i = 0; i < poolSize; i++) {
            if (connections[i] == connection){
                status.set(i,0);
                log.debug("{}","free....");
                semaphore.release();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Pooll pooll = new Pooll(2);
        for (int i = 0; i < 5; i++) {
            new Thread(()->{
               Connection connection= pooll.borrow();
                try {
                    Thread.sleep(new Random().nextInt(1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                pooll.free(connection);
            }).start();
        }
    }
}
