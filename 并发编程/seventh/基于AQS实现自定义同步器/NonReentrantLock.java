package com.并发编程.seventh.基于AQS实现自定义同步器;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 自定义不可重入锁
 */
public class NonReentrantLock implements Lock ,java.io.Serializable{

//    内部帮助类
    private static class Sync extends AbstractQueuedSynchronizer{
    /**
     *  state ： 1  已经加锁   0  未加锁
     * @param arg
     * @return
     */

    @Override
    protected boolean tryAcquire(int arg) {
         if (compareAndSetState(0,1)){
             setExclusiveOwnerThread(Thread.currentThread());
             return true;
         }
         return false;
    }

    @Override
    protected boolean tryRelease(int arg) {
        if (getState() == 0){
            throw new IllegalMonitorStateException();
        }
        setExclusiveOwnerThread(null);
        setState(0);
        return true;
    }


    @Override
    protected boolean isHeldExclusively() {
        return getState() == 1;
    }

    public Condition newCondition() {
        return new ConditionObject();
    }
}

   private final Sync sync = new Sync();
    @Override
    public void lock() {
         sync.acquire(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
       sync.acquireInterruptibly(1);
    }

    @Override
    public boolean tryLock() {
        return sync.tryAcquire(1);
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireSharedNanos(hashCode(),unit.toNanos(time));
    }

    @Override
    public void unlock() {
        sync.release(1);
    }

    @Override
    public Condition newCondition() {
        return sync.newCondition();
    }
}
