package com.IO.NIO.入门案例.server;


import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class Servlet1 {
    public static void main(String[] args) throws Exception {
        System.out.println("服务的启动");
//        1.该通道专门用于客户端第一次的连接
//        可以发现本质行BIO模数据交互的方式就是：通过一个选择器来维护客户都和服务端，不同的是，服务的的通道只有一个，并且通道绑定了一个端口号
//        然后将该通道注册到选择器中，当有客户端请求连接该端口服务的时，就会触发选择器的连接事件，然后。。。。
        ServerSocketChannel sschannel = ServerSocketChannel.open();
//        2.注册基于非阻塞式的
        sschannel.configureBlocking(false);
//        3.绑定连接的端口
        sschannel.bind(new InetSocketAddress(9999));
//        4.获取其选择器
        Selector selector = Selector.open();
//        5.将通道都注册到选择器上(监听客户端的连接)
        sschannel.register(selector, SelectionKey.OP_ACCEPT);
//        6.使用Selector选择器轮询已经注册的通道，看是否有已经就绪好的事件
//        如果有事件发生select（）才大于0
//        这个方法时阻塞的，
        while (selector.select() > 0) {
            System.out.println("开始新一轮的处理");
//            7.获取选择器中已经就绪的事件 SelectKey 就是事件
            Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
//            遍历这些准备好的事件
            while (keyIterator.hasNext()) {

                SelectionKey sk = keyIterator.next();
//                9.判断事件具体是什么
                if (sk.isAcceptable()) {
//                    判断是不是接入事件 获取客户端通道
                    System.out.println("客户都接入了");
                    SocketChannel sc = sschannel.accept();
                    sc.configureBlocking(false);
//                    将客户端注册到选择器，注意这里选择器监听的时read 事件，但是相对于客户都来说是写
                    sc.register(selector,SelectionKey.OP_READ);
                }
//                读事件
                else if (sk.isReadable()){
//                     读事件，则获取当前选择器上就绪好的读事
//                    获取这个事件触发的通道 , 读取客户都的信息，获取触发该事件的通道
                    SocketChannel socketChannel = (SocketChannel) sk.channel();
//                    通过 客户都通道来读取数据
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    int len = 0;
                    while ((len = socketChannel.read(buffer)) > 0){
//                        每次读取数据时，都需要切换至读取模式
                        buffer.flip();
                        System.out.println(new String(buffer.array(),0,len));
                        buffer.clear();
                    }

                }
                keyIterator.remove();
            }
        }
    }
}
