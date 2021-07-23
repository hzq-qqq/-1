package com.IO.NIO.main;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {
        SocketChannel sc = SocketChannel.open(new InetSocketAddress("127.0.0.1",9999));
        sc.configureBlocking(false);
        ByteBuffer buffer = ByteBuffer.allocate(10);
        Scanner sin = new Scanner(System.in);
        while (true){
            String msg = sin.nextLine();
            System.out.println(msg);
            buffer.put(msg.getBytes(StandardCharsets.UTF_8));
            buffer.flip();
            sc.write(buffer);
            buffer.clear();
        }
    }
}
