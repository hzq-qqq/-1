package com.IO.NIO.入门案例.client;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws Exception{
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1",9999));
        socketChannel.configureBlocking(false);

        ByteBuffer buffer = ByteBuffer.allocate(1024);

//        发送数据给服务端
        Scanner sin = new Scanner(System.in);
        while (true){
            System.out.println("请说");
            String msg = sin.next();
            buffer.put(("张三" + msg).getBytes(StandardCharsets.UTF_8));
            buffer.flip();
//            向通道中写入buffer中的数据
            socketChannel.write(buffer);
            buffer.clear();
        }

    }
}
