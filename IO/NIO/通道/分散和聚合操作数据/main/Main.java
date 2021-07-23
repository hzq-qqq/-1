package com.IO.NIO.通道.分散和聚合操作数据.main;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class Main {
    public static void main(String[] args) throws Exception{
        FileInputStream fis = new FileInputStream("D:\\学习源码\\untitled\\src\\a.txt");
        FileChannel fisc = fis.getChannel();

        FileOutputStream fos = new FileOutputStream("D:\\学习源码\\untitled\\src\\b.txt");
        FileChannel fosc = fos.getChannel();

        ByteBuffer buffer1 = ByteBuffer.allocate(4);
        ByteBuffer buffer2 = ByteBuffer.allocate(1024);
        ByteBuffer[] buffers = {buffer1,buffer2};

//        将数据从通道中读取到缓冲区中
        fisc.read(buffers);
        for (ByteBuffer buffer : buffers) {
//            切换到读取数据模式
            buffer.flip();
            System.out.println(new String(buffer.array(),0,buffer.remaining()));
        }

//       将多个换从区的数据写入到另一个通道中
        fosc.write(buffers);
        fisc.close();
        fosc.close();
        System.out.println("文件复制");


    }
}
