package com.并发编程.fourth.自定义锁;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 自定锁 不可重入锁
 */
public class TestAqs implements Lock {

    class MySync extends AbstractQueuedSynchronizer {
        protected void Mylock() {
        }

        /**
         * 尝试获取
         * @param arg
         * @return
         */
        @Override
        protected boolean tryAcquire(int arg) {
           if (compareAndSetState(0,1)){
//               加上了锁，这是owner 为当前线程
               setExclusiveOwnerThread(Thread.currentThread());
               return true;
           }
//           加锁失败
            return false;
        }

        /**
         * 尝试释放
         * @param arg
         * @return
         */
        @Override
        protected boolean tryRelease(int arg) {
//            因为在这里是已经获取到锁时，才可能回去释放当前的锁，所以这里就不需要在进行加锁操作
//            直接将状体置为 0 即可
//            将owner 设置为null 表示当前没有线程占用
            setExclusiveOwnerThread(null);
            setState(0);
//            这里将state 使用了volatile 修饰 ，这里时写操作，可以防止指令的重排序问题(前面将owner 设置为null)
            return true;
        }

        /**
         * 是否持有独占锁  —— 持有1  表示占有独占锁
         * @return
         */
        @Override
        protected boolean isHeldExclusively() {
           return getState() == 1;
        }


        public Condition newCondition() {
            return null;
        }
    }

    private MySync sync= new MySync();
    /**
     * 加锁 —— 不成功就会进入等待队列等待
     */
    @Override
    public void lock() {
     sync.acquire(1);
    }

    /**
     * 可打断的加锁
     * @throws InterruptedException
     */
    @Override
    public void lockInterruptibly() throws InterruptedException {
          sync.acquireInterruptibly(1);
    }

    /**
     * 尝试加锁
     * @return
     */
    @Override
    public boolean tryLock() {
        return sync.tryAcquire(1);
    }

    /**
     * 限时尝试加锁
     * @param l
     * @param timeUnit
     * @return
     * @throws InterruptedException
     */
    @Override
    public boolean tryLock(long l, TimeUnit timeUnit) throws InterruptedException {
       return sync.tryAcquireNanos(1,timeUnit.toNanos(l));
    }

    /**
     * 释放锁
     */
    @Override
    public void unlock() {
           sync.release(1);
    }

    /**
     * 条件变量
     * @return
     */
    @Override
    public Condition newCondition() {
        return sync.newCondition();
    }

    public boolean isLock(){
        return sync.isHeldExclusively();
    }
}
