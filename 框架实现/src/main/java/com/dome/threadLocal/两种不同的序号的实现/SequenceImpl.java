package com.dome.threadLocal.两种不同的序号的实现;

import com.dome.threadLocal.ClientThread;
import com.dome.threadLocal.Sequence;

public class SequenceImpl implements Sequence {
    private static int number = 0;
    @Override
    public int getNumber() {
        number++;
        return number;
    }

    public static void main(String[] args) {
        Sequence sequence = new SequenceImpl();
        ClientThread thread1 = new ClientThread(sequence);
        ClientThread thread2 = new ClientThread(sequence);
        ClientThread thread3 = new ClientThread(sequence);

        thread1.start();
        thread2.start();
        thread3.start();
    }
}
