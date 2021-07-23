package com.IO.NIO.通道.读取文件中的数据.main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class Main {
    public static void main(String[] args) throws IOException {
//        需要使用原始流来定位到文件
        FileInputStream fis = new FileInputStream("D:\\学习源码\\untitled\\src\\a.txt");
        FileChannel fisc = fis.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(100);

        fisc.read(buffer);
        System.out.println(buffer.position());
        System.out.println(buffer.limit());
        buffer.flip();
        System.out.println("======================");
        System.out.println(buffer.position());
        System.out.println(buffer.limit());
        String s = new String(buffer.array(),0,buffer.remaining());
        System.out.println(s);

    }
}
