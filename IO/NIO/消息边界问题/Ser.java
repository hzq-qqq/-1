package com.IO.NIO.消息边界问题;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;

public class Ser {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel ssc  = ServerSocketChannel.open();
        ssc.configureBlocking(false);
        ssc.bind(new InetSocketAddress(9999));
        Selector selector = Selector.open();
        ssc.register(selector, SelectionKey.OP_ACCEPT);
        while (selector.select() > 0){
            Iterator<SelectionKey> it = selector.selectedKeys().iterator();
            while (it.hasNext()){
                SelectionKey sk = it.next();
                try {
                    if (sk.isAcceptable()) {
                        System.out.println("一个用户连接了");
                        SocketChannel accept = ssc.accept();
                        accept.configureBlocking(false);
                        ByteBuffer buffer = ByteBuffer.allocate(4);
                        accept.register(selector, SelectionKey.OP_READ,buffer);
                    } else if (sk.isReadable()) {
                        SocketChannel sc = (SocketChannel) sk.channel();
                        ByteBuffer   buffer = (ByteBuffer) sk.attachment();
                        int read = sc.read(buffer);
                        if (read == -1) {
                            sk.cancel();
                        } else {
                            buffer.flip();
                            System.out.println(Charset.defaultCharset().decode(buffer));
                            if (buffer.position() == buffer.limit()){
                                System.out.println("读取数据失败因为，服务端buffer的长度不足");
                                ByteBuffer newBuffer = ByteBuffer.allocate(buffer.capacity() * 2);
                                buffer.flip();
                                newBuffer.put(buffer);
                                sk.attach(newBuffer);
                            }
                        }
                    }
                    it.remove();
                }
                catch (Exception e){
                    System.out.println("用户下线了");
                    sk.cancel();
                }
            }
        }
    }
}
