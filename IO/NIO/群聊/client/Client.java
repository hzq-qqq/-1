package com.IO.NIO.群聊.client;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Scanner;

public class Client {
//    客户端代码逻辑的实现

    private Selector selector;
    private SocketChannel sc;
    private final static int PORT = 9999;
    public Client(){
        try {
            sc = SocketChannel.open(new InetSocketAddress("127.0.0.1",PORT));
            sc.configureBlocking(false);
            selector = Selector.open();
            sc.register(selector, SelectionKey.OP_READ);
            System.out.println("客户端准备完成");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void sendMsg(String msg){
        try {
            sc.write(ByteBuffer.wrap(("张三说： " + msg).getBytes(StandardCharsets.UTF_8)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args)throws Exception {
        Client client = new Client();
//        新开启一个线程来进行读取服务器的消息
        new Thread(()->{
            while (true){
                try {
                    client.listen();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

//        用于发送信息服务端的线程
        Scanner sin = new Scanner(System.in);
        while (sin.hasNextLine()){
            String msg = sin.nextLine();
            client.sendMsg(msg);
        }
    }


    private void listen() throws IOException {
         while (selector.select() > 0 ){
             Iterator<SelectionKey> it = selector.selectedKeys().iterator();
             while (it.hasNext()){
                 SelectionKey key = it.next();
                 if (key.isReadable()){
                     SocketChannel socketChannel = (SocketChannel) key.channel();
                     ByteBuffer buffer = ByteBuffer.allocate(1024);
//                     将通道中的信息读取到buffer 中
                     socketChannel.read(buffer);
                     System.out.println("张三接受到服务器的消息是 ： " + new String(buffer.array(),0,1024).trim());
                 }
             }
//             移除当前监听的事件
             it.remove();
         }
    }


}
