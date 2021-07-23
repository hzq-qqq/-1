package com.IO.BIO.复习案例.buffer;

import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class Main {
    public static void main(String[] args) throws IOException {
        FileChannel fc = new FileInputStream("D:\\学习源码\\untitled\\src\\a.txt").getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(5);

        int len = 0;
        while (true) {
            len = fc.read(buffer);

            if (len == -1) {
                break;
            }
            System.out.println("读取到的字节数 " + len);
//            切换至读模式
            buffer.flip();
            while (buffer.hasRemaining()){
                byte b = buffer.get();
                System.out.println((char)b);

            }
//            每次读取完后需要清除当前缓冲区
            buffer.clear();

        }
    }
}
