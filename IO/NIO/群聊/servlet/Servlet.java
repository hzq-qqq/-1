package com.IO.NIO.群聊.servlet;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

public class Servlet {

    private Selector selector;
    private ServerSocketChannel sschannel;
    private final static int PORT = 9999;

    public Servlet(){
        try {
            sschannel = ServerSocketChannel.open();
            sschannel.configureBlocking(false);
            sschannel.bind(new InetSocketAddress(PORT));
            selector = Selector.open();
            sschannel.register(selector, SelectionKey.OP_ACCEPT);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void listen()  {
        System.out.println("监听线程——》" + Thread.currentThread().getName());
        try {
            while (selector.select() > 0) {
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()){
                    SelectionKey sk = iterator.next();
                    if (sk.isAcceptable()){
                        SocketChannel accept = sschannel.accept();
                        System.out.println(accept.getRemoteAddress() + "已连接");
                        accept.configureBlocking(false);
                        accept.register(selector,SelectionKey.OP_READ);
                    }else if (sk.isReadable()){
                          readClient(sk);
                    }
//                    注意处理完之后需要移除该事件，避免下一次重复处理
                    iterator.remove();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 用于接受当前客户端通道的信息，然后转发给其他通道
     * @param sk
     */
    private void readClient(SelectionKey sk)  {
            SocketChannel sc = null;
            try{
                sc = (SocketChannel) sk.channel();
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                int count = sc.read(buffer);
                if (count > 0){
//                    提取读取到的信息
                    String msg = new String(buffer.array());
                    System.out.println("接受到了客户端你的消息: " + msg);
//                    把这个消息推送给全部的客户端
                    sendMsgAll(msg,sc);
                }
            }catch (Exception e){
                try {
//                   离线指的是用户发送完数据就离线了，然后在使用该客户端对应的通道来读取数据时就会报错
                    assert sc != null;
                    System.out.println("有人离线了 " + sc.getRemoteAddress());
                    sk.cancel();
                    sc.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
    }

//   把当前客户端的消息数据推送给全部在线注册的客户端

    private void sendMsgAll(String msg, SocketChannel sc) throws IOException {
        System.out.println("服务的开始转发消息给客户端 " + "当前处理的线程是 " + sc.getRemoteAddress());
        for (SelectionKey key : selector.keys()) {
            //通过 key  取出对应的 SocketChannel
            Channel targetChannel = key.channel();
            //排除自己
            if(targetChannel instanceof  SocketChannel && targetChannel != sc) {
                //转型
                SocketChannel dest = (SocketChannel)targetChannel;
                //将msg 存储到buffer
                ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
                //将buffer 的数据写入 通道
                dest.write(buffer);
            }
        }
    }

    public static void main(String[] args) throws Exception{
        Servlet servlet = new Servlet();
        servlet.listen();
    }

}
