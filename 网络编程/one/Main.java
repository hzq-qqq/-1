package com.网络编程.one;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class Main {
    public static void main(String[] args) throws IOException {
        FileChannel fc = new FileInputStream("D:\\学习源码\\学习\\src\\a.txt").getChannel();
//        划分10个字节的缓冲区
        ByteBuffer bf = ByteBuffer.allocate(10);
//        取出buffer 并打印
//        切换到读取模式
//        分多次读取数据
        while (true){
            int lenth = fc.read(bf);
            if (lenth == -1){
                break;
            }
            bf.flip();
            while (bf.hasRemaining()){
                byte b = bf.get();
                System.out.println((char) b);
            }
//            读取完数据之后切换至写模式
            bf.clear();
        }
    }
}
