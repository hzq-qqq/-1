package com.IO.NIO.通道.写入数据到文件中;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

public class Main {
    public static void main(String[] args) throws IOException {
        FileOutputStream fos = new FileOutputStream("D:\\学习源码\\untitled\\src\\a.txt");
        FileChannel fosc = fos.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(100);
        buffer.put("写入数据到文件a中".getBytes(StandardCharsets.UTF_8));
//将位置置为0
        buffer.flip();
        fosc.write(buffer);
        fosc.close();
    }
}
