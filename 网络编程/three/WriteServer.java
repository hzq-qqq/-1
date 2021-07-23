package com.网络编程.three;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

public class WriteServer {
    public static void main(String[] args) throws Exception{
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);
        Selector selector = Selector.open();
        ssc.register(selector, SelectionKey.OP_ACCEPT);
        ssc.bind(new InetSocketAddress(8080));

        while (true){
            selector.select();
            Iterator<SelectionKey> it = selector.selectedKeys().iterator();
            while (it.hasNext()){
                SelectionKey sk = it.next();
                it.remove();
                if (sk.isAcceptable()){
                    SocketChannel sc = ssc.accept();
                    sc.configureBlocking(false);
                    SelectionKey scKey = sc.register(selector, 0, null);
                   scKey.interestOps(SelectionKey.OP_READ);
                    StringBuilder sb = new StringBuilder();
//                    向客户端发送大量的数据
                    for (int i = 0; i < 5000000; i++) {
                        sb.append("a");
                    }
                    ByteBuffer buffer = StandardCharsets.UTF_8.encode(sb.toString());
//                    返回值代表实际写入的数据的字节数
                    int len = 0;
//                   while (buffer.hasRemaining()){
//                       int lenth = sc.write(buffer);
//                       System.out.println("实际写入的字节数为 ：" + lenth);
//                   }
//                    添加一个可写事件实现非阻塞（可写 —— 缓冲区未满）
                   if (buffer.hasRemaining()){
//                     关注可写事件             1 —— 读事件的标志            5 —— 写事件的标志
                      scKey.interestOps(scKey.interestOps() + SelectionKey.OP_WRITE);
//                      将buffer 挂到 selectKey 上
                      scKey.attach(buffer);
                   }
                    /**
                     * 这里相当于是把原来的while循环换位多个可写事件来来处理 —— 实现了非阻塞的思想
                     */
                }else if (sk.isWritable()){
//                     取出上一次的buffer
                    ByteBuffer buffer = (ByteBuffer) sk.attachment();
                    SocketChannel sc = (SocketChannel) sk.channel();
                    int lenth = sc.write(buffer);
                    System.out.println("写入的细节数量未：" + lenth);

//                    清理操作
                    if (!buffer.hasRemaining()){
//                        buffer 中的内容读取完了  新关联一个 buffer 就会将旧的buffer覆盖掉
                        sk.attach(null);
//                        取消关注write事件
                        sk.interestOps(sk.interestOps() - SelectionKey.OP_WRITE);
                    }
                }
            }
        }
    }
}
