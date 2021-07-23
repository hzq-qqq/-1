package com.并发编程.seven.多个线程交替执行;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class ReentrantLockJj extends ReentrantLock {
    private int loopNum;

    public ReentrantLockJj() {
    }

    public ReentrantLockJj(int loopNum) {
        this.loopNum = loopNum;
    }

    public int getLoopNum() {
        return loopNum;
    }

    public void setLoopNum(int loopNum) {
        this.loopNum = loopNum;
    }

    public void start(Condition condition){
        try {
            this.lock();
            condition.signalAll();
        }finally {
            this.unlock();
        }
        log.debug("start ");

    }

    public void print(String msg, Condition condition,Condition ncondition){
        for (int i = 0; i < loopNum; i++) {

            try {
                this.lock();
                condition.await();
                log.debug(msg);
                ncondition.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                this.unlock();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ReentrantLockJj reentrantLockJj = new ReentrantLockJj(3);
        Condition aCondition = reentrantLockJj.newCondition();
        Condition bCondition = reentrantLockJj.newCondition();
        Condition cCondition = reentrantLockJj.newCondition();
        Thread t1 = new Thread(() -> {
            reentrantLockJj.print("a",aCondition,bCondition);
        }, "t1");
        Thread t2 = new Thread(() -> {
            reentrantLockJj.print("b",bCondition,cCondition);
        }, "t2");
        Thread t3 = new Thread(() -> {
            reentrantLockJj.print("c",cCondition,aCondition);
        }, "t3");
        t1.start();
        t2.start();
        t3.start();
        Thread.sleep(1);
        reentrantLockJj.start(aCondition);
    }
}
