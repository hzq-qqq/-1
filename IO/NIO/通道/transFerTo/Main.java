package com.IO.NIO.通道.transFerTo;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

public class Main {
    public static void main(String[] args) throws Exception {
        FileChannel fisc = new FileInputStream("D:\\学习源码\\untitled\\src\\a.txt").getChannel();

        FileChannel fosc = new FileOutputStream("D:\\学习源码\\untitled\\src\\b.txt").getChannel();

        fisc.transferTo(fisc.position(),fisc.size(),fosc);
        fisc.close();
        fosc.close();
    }
}
