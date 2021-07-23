package com.网络编程.two.练习;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * 服务器端
 */
@Slf4j
public class Main3 {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.bind(new InetSocketAddress(8080));
        ssc.configureBlocking(false);
        Selector selector = Selector.open();
        SelectionKey sk = ssc.register(selector, 0, null);
        sk.interestOps(SelectionKey.OP_ACCEPT);

       while (true){
//           有事件发生才会出继续向下执行
             selector.select();
//             包含所有的发生的事件 , 处理可用事件
           Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
           while (iterator.hasNext()){
               SelectionKey selectionKey = iterator.next();
               iterator.remove();
               log.debug("redistkey : {}",selectionKey);
//               区分事件
               try {
                   if (selectionKey.isAcceptable()) {
                       ServerSocketChannel channel = (ServerSocketChannel) selectionKey.channel();
                       SocketChannel sc = channel.accept();
                       sc.configureBlocking(false);
                       sc.register(selector, SelectionKey.OP_READ);
                   } else if (selectionKey.isReadable()) {
//                   处理读事件
                       SocketChannel channel = (SocketChannel) selectionKey.channel();
                       ByteBuffer buffer = ByteBuffer.allocate(16);
                       int read = channel.read(buffer);
//                       这里是处理异常断开的情况 —— 异常断开返回的是-1
                       if (read == -1){
//                          正常断开返回-1
                           selectionKey.cancel();
                       }                       buffer.flip();
                       System.out.println(StandardCharsets.UTF_8.decode(buffer));
                   }
               }catch (Exception e){
//                   异常断开
                   selectionKey.cancel();
               }
           }
       }
    }
}
