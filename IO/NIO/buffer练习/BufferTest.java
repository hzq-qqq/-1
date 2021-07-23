package com.IO.NIO.buffer练习;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class BufferTest {
    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(20);
        System.out.println(byteBuffer.position());
        System.out.println(byteBuffer.limit());
        System.out.println(byteBuffer.capacity());
        System.out.println("===================");
        String msg = "hello";
        String msg1 = "hello world";
        byteBuffer.put(msg.getBytes(StandardCharsets.UTF_8));
        byteBuffer.put(msg1.getBytes(StandardCharsets.UTF_8));
        System.out.println(byteBuffer.position());
        System.out.println(byteBuffer.limit());
        System.out.println(byteBuffer.capacity());
        System.out.println("===================");
        char b1 = (char) byteBuffer.get(13);
        System.out.println(byteBuffer.position());
        System.out.println(b1);
//        byteBuffer.flip();
//        System.out.println(byteBuffer.position());
        char b = (char) byteBuffer.get(13);
        System.out.println(b);
        System.out.println("===================");
        byteBuffer.clear();
        System.out.println(byteBuffer.position());
        System.out.println((char)byteBuffer.get(12));
    }
}
