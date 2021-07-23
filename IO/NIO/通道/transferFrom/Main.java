package com.IO.NIO.通道.transferFrom;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

public class Main {
    public static void main(String[] args)throws Exception {
        FileChannel fisc = new FileInputStream("D:\\学习源码\\untitled\\src\\a.txt").getChannel();

        FileChannel fosc = new FileOutputStream("D:\\学习源码\\untitled\\src\\b.txt").getChannel();

//        fosc 是目标通道,后面的参数是源通道

        fosc.transferFrom(fisc,fisc.position(),fisc.size());
        fosc.close();
        fisc.close();

    }
}
