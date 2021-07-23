package com.IO.NIO.直接内存.main;

import java.nio.ByteBuffer;

public class Main {
    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(10);
        System.out.println(byteBuffer.isDirect());
        ByteBuffer allocate = ByteBuffer.allocate(10);
        System.out.println(allocate.isDirect());
    }
}
