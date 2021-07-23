package com.网络编程.two.练习;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class Client {
    public static void main(String[] args) throws IOException {
        SocketChannel open = SocketChannel.open();
        open.connect(new InetSocketAddress("localhost",8080));

        System.out.println("waiting....");
    }
}
