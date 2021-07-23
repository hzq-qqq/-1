package com.网络编程.sex;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class Client {
    public static void main(String[] args) throws IOException {
        final SocketChannel sc = SocketChannel.open();
        sc.connect(new InetSocketAddress("localhost",9543));
        sc.write(StandardCharsets.UTF_8.encode("11111"));
        System.in.read();

    }
}
