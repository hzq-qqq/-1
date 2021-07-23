package com.并发编程.seven.多个线程交替执行;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WaitNotify {
//    这里是控制线程运行
    private int flag;
//    控制线程的执行的顺序
    private int loopNum;

    public WaitNotify() {
    }

    public WaitNotify(int flag, int loopNum) {
        this.flag = flag;
        this.loopNum = loopNum;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public int getLoopNum() {
        return loopNum;
    }

    public void setLoopNum(int loopNum) {
        this.loopNum = loopNum;
    }

    public void pring(int waitflag, int nextFlag,String str){
        synchronized (this){
            while (waitflag != flag){
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            log.debug(str);
            flag = nextFlag;
            this.notifyAll();
        }
    }

    public static void main(String[] args) {
        WaitNotify notify = new WaitNotify(1,5);
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < notify.getLoopNum(); i++) {
                notify.pring(1,2,"a");
            }
        }, "t1");

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < notify.getLoopNum(); i++) {
                notify.pring(2,3,"b");
            }
        }, "t2");

        Thread t3 = new Thread(() -> {
            for (int i = 0; i < notify.getLoopNum(); i++) {
                notify.pring(3,1,"c");
            }
        }, "t3");

        t1.start();
        t2.start();
        t3.start();
    }
}
