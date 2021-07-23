package com.dome.threadLocal.自己实现ThreadLocal;

import com.dome.threadLocal.ClientThread;
import com.dome.threadLocal.Sequence;

public class SequenceCimpl implements Sequence {
    private static MyThreadLocal<Integer> conrtainer = new MyThreadLocal<Integer>(){
//        初始化值我 0；
        @Override
        protected Integer initialValue() {
            return 0;
        }
    };
    @Override
    public int getNumber() {
        conrtainer.set(conrtainer.get() + 1);
        return conrtainer.get();
    }

    public static void main(String[] args) {
        Sequence sequence  = new SequenceCimpl();

        ClientThread thread1=  new ClientThread(sequence);
        ClientThread thread2=  new ClientThread(sequence);
        ClientThread thread3=  new ClientThread(sequence);

        thread1.start();
        thread2.start();
        thread3.start();
    }
}
