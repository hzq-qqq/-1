package com.网络编程.four;

import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class Client {
    public static void main(String[] args)throws Exception {
        SocketChannel sc = SocketChannel.open();
        sc.connect(new InetSocketAddress("localhost",8080));
        sc.write(StandardCharsets.ISO_8859_1.encode("1234567890abc"));

        System.out.println("read...");
    }
}
