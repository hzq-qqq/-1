package com.IO.NIO.通道.文件复制;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class Main {
    public static void main(String[] args) throws Exception {
        FileInputStream fis = new FileInputStream("D:\\学习源码\\untitled\\src\\a.txt");
        FileOutputStream fos = new FileOutputStream("D:\\学习源码\\untitled\\src\\b.txt");

        ByteBuffer buffer = ByteBuffer.allocate(1024);

        FileChannel fisc = fis.getChannel();
        FileChannel fosc = fos.getChannel();
        while (true) {
//            注意这里需要归为操作,因为一次数据不一定能够全部读取完,所以每次读取数据都需要将位置,limit归位
            buffer.clear();
            int read = fisc.read(buffer);
            if (read == -1){
                break;
            }
//            将buffer切换为读模式
            buffer.flip();
            fosc.write(buffer);
        }
    }
}
