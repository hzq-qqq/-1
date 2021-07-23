package com.dome.threadLocal.两种不同的序号的实现;

import com.dome.threadLocal.ClientThread;
import com.dome.threadLocal.Sequence;

public class SequenceBImpl implements Sequence {
//    封装了一个 静态变量 numberContainer
    private static ThreadLocal<Integer> numberContainer = new ThreadLocal<Integer>(){
//        容器的初始值为0
        @Override
        protected Integer initialValue() {
            return 0;
        }
    };
    @Override
    public int getNumber() {
//        这里是每次从一个线程独有的map中获取值
        numberContainer.set(numberContainer.get() + 1);
        return numberContainer.get();
    }

    public static void main(String[] args) {
        Sequence sequence = new SequenceBImpl();

        ClientThread thread1 = new ClientThread(sequence);
        ClientThread thread2 = new ClientThread(sequence);
        ClientThread thread3 = new ClientThread(sequence);

        thread1.start();
        thread2.start();
        thread3.start();
    }
}
