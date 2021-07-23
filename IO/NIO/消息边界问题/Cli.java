package com.IO.NIO.消息边界问题;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class Cli {
    public static void main(String[] args) throws Exception {
        SocketChannel sc = SocketChannel.open();
        sc.connect(new InetSocketAddress("localhost",9999));
        sc.configureBlocking(false);


        System.out.println("waiting ");
        System.out.println("waiting ");
    }
}
