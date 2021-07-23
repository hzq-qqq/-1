package com.并发编程.fifth;

import lombok.extern.slf4j.Slf4j;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class TestReadeWriteLock {



    @Slf4j(topic = "c.dataContainer")
    static class DataContainer{
        private Object data;
        private ReentrantReadWriteLock rw = new ReentrantReadWriteLock();
        private ReentrantReadWriteLock.ReadLock r = rw.readLock();
        private ReentrantReadWriteLock.WriteLock w = rw.writeLock();

        public Object read() throws InterruptedException {
            log.debug("获取读锁");
            r.lock();
            try{
                log.debug("读取");
                Thread.sleep(1);
                return data;
            } finally {
                log.debug("释放读锁");
                r.unlock();;
            }
        }

        public void write(){
            log.debug("获取写锁");
            w.lock();
            try{
                log.debug("写入信息");
            }finally {
                log.debug("释放写锁");
                w.unlock();
            }
        }
    }

    /**
     * 读读并发
     * 读写阻塞
     * 写写阻塞
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        DataContainer container = new DataContainer();
        new Thread(()->{
            container.write();
        },"t1").start();
        new Thread(()->{
            try {
              container.write();
            }catch (Exception e){
                e.printStackTrace();

            }
            },"t2").start();
    }
    /**
     * 注意读写锁不支持锁的降级 —— 当获取到读锁之后又去获取写锁就会导致永久的等待
     * 支持 重入锁的降级 —— 持有写锁的线程 还可以获得读锁
     * 
     */
}
