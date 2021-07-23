package com.并发编程.sexth.读写锁案例练习;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 这里在调用get（）方法的时候，使用的是读锁，可以实现读读共享
 */
public class ReentrantLockTest {
    private ArrayList<String> array = new ArrayList<>();
    private final ReentrantReadWriteLock rw = new ReentrantReadWriteLock();
    private final Lock readLock = rw.readLock();
    private final Lock writeLock = rw.writeLock();

    public void add(String value){
        writeLock.lock();
        try {
            array.add(value);
        }finally {
            writeLock.unlock();
        }
    }

    public void remove (String value){
        writeLock.lock();
        try {
            array.remove(value);
        }finally {
            writeLock.unlock();
        }
    }

    public String get(int index){
        readLock.lock();
        try {
            return array.get(index);
        }finally {
            readLock.unlock();
        }
    }
}
