package com.IO.NIO.main;



import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

public class Servler {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);
        ssc.bind(new InetSocketAddress(9999));
        Selector selector = Selector.open();
        ssc.register(selector, SelectionKey.OP_ACCEPT);
        while (selector.select() > 0){
            Iterator<SelectionKey> it = selector.selectedKeys().iterator();
            System.out.println("有事件触发了");
            while (it.hasNext()){
                SelectionKey sk = it.next();
                if (sk.isAcceptable()){
                    System.out.println("有新的客户端接入： ");
                    SocketChannel sc = ssc.accept();
                    sc.configureBlocking(false);
                    sc.register(selector,SelectionKey.OP_READ);
                }
                else if (sk.isReadable()) {
//                    这里注意当客户端挂掉以后会触发一个读事件，所以需要堆异常进行处理，不让服务器挂掉
                    try {
                        System.out.println("读取事件执行了");
                        SocketChannel sc = (SocketChannel) sk.channel();
                        ByteBuffer buffer1 = ByteBuffer.allocate(4);
                        int len = sc.read(buffer1);
                        System.out.println(len);
                        buffer1.flip();
                        System.out.println(new String(buffer1.array(), 0, len));
                    }catch (Exception e){
//                        1.将该断开的事件删除，以及删除客户端的channel（异常断开）
//                        2.将该客户端绑定的事件删除掉
//                        这里捕获的是Exception 可以捕获异常断开和正常断开的情况
                        System.out.println("一个客户端下线了");
                         sk.cancel();
                    }
                };
//                这里在每次事件执行完成之后，事件自己不会主动的消失，而是会一直存在于迭代器中
//                如果不手动的去删除事件那么就会导致事件被重复的执行
                it.remove();
            }
        }
    }
}
