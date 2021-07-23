package com.网络编程.two.处理消息边界的问题;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class Client {
    public static void main(String[] args) throws IOException {
        SocketChannel open = SocketChannel.open();
        open.connect(new InetSocketAddress("localhost",8080));

        open.write(StandardCharsets.UTF_8.encode("hello\nworld\n"));
        System.out.println("waiting....");
    }
}
