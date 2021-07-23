package com.网络编程.two.处理消息边界的问题;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

/**
 * 处理消息边界问题  ——是要是 TCP 编程都需要考虑 消息边界问题
 *
 */
@Slf4j
public class Main3 {
    private static void accept(ByteBuffer buffer) {
//        通过分隔符号来分割数据的
        buffer.flip();
        for (int i = 0; i < buffer.limit(); i++) {
            if (buffer.get(i) == '\n'){
//                将消息存储如新的buffer中 计算buffer 的大小
                int lenth = i + 1 - buffer.position();
                ByteBuffer target = ByteBuffer.allocate(lenth);
//              读取数据
                for (int i1 = 0; i1 < lenth; i1++) {
                    target.put(buffer.get());
                }
                target.flip();
                System.out.println(StandardCharsets.UTF_8.decode(target));
            }
        }
//        本读取的数据可能是部分数据为读取完整
//        这里假如没有读取到信息，那么在调用conpact方法byteBuffer中依然没有数据
//        考虑扩容
//        当buffer 的positon 和  limit 值一样的时候就表示 当前内存不足够读取数 就需要进行扩容
        buffer.compact();
    }
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
//               区分事件
               try {
                   if (selectionKey.isAcceptable()) {
                       ServerSocketChannel channel = (ServerSocketChannel) selectionKey.channel();
                       SocketChannel sc = channel.accept();
                       sc.configureBlocking(false);
                       ByteBuffer buffer = ByteBuffer.allocate(5);
//                       将byteBuffer 作为 附件 关联到事件上
                       sc.register(selector, SelectionKey.OP_READ,buffer);
                   } else if (selectionKey.isReadable()) {
//                   处理读事件
                       SocketChannel channel = (SocketChannel) selectionKey.channel();
//                       c事件中获取ByteBuffer
//                       该byteBuffer 的生命周期是和该客户端绑定
                       ByteBuffer buffer = (ByteBuffer) selectionKey.attachment();
                       int read = channel.read(buffer);
//                       这里是处理异常断开的情况 —— 异常断开返回的是-
                       if (read == -1){
//                          正常断开返回-1
                           selectionKey.cancel();
                       }
                      accept(buffer);
                       if (buffer.position() == buffer.limit()){
                           log.debug("扩容");
//                           扩容
                           ByteBuffer newBuffer = ByteBuffer.allocate(buffer.capacity() * 2);
                           buffer.flip();
                           newBuffer.put(buffer);
//                           将newBuffer 替换掉原有的事件的buffer
                           selectionKey.attach(newBuffer);
                       }
                   }
               }catch (Exception e){
//                   异常断开
                   selectionKey.cancel();
               }
           }
       }
    }
}
