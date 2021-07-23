package com.网络编程.four;

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

@Slf4j
public class MultiThreadServer {
    public static void main(String[] args) throws Exception{
        Thread.currentThread().setName("Boss");
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);
        ssc.bind(new InetSocketAddress(8080));
        Selector boss = Selector.open();
        ssc.register(boss, SelectionKey.OP_ACCEPT);
//        创建一个新的work 来处理该客户端的读写
        Worker worker = new Worker("worker-0");
        worker.register();
        while (true){
            boss.select();
            Iterator<SelectionKey> it = boss.selectedKeys().iterator();
            while (it.hasNext()){}
            SelectionKey sk = it.next();
            if (sk.isAcceptable()){
                SocketChannel sc = ssc.accept();
                sc.configureBlocking(false);
                log.debug("connecten....{}",sc.getRemoteAddress());
//              将新的socketChannel 与worker关联
                log.debug("before..connected..{}",sc.getRemoteAddress());
                sc.register(worker.selector,SelectionKey.OP_READ);
                log.debug("after..connected..{}",sc.getRemoteAddress());
            }
        }
    }

    /**
     * work 专门用于检测读写事件
     */
    static class Worker implements Runnable{
        private Thread t;
        private Selector selector;
        private String name;
        private Boolean flag = false;

        public Worker(String name) {
            this.name = name;
        }

        /**
         * 初始化线程和seletor
         */
        public void register() throws IOException {
            if (flag){
                t = new Thread(this);
                t.start();
                selector = Selector.open();
                flag = true;
            }

        }

        @Override
        public void run() {
          while (true){
              try {
                  selector.select();
                  Iterator<SelectionKey> it = selector.selectedKeys().iterator();
                  while (it.hasNext()){
                      SelectionKey sk = it.next();
                      it.remove();
                      if (sk.isReadable()){
                          ByteBuffer buffer = ByteBuffer.allocate(16);
                          SocketChannel sc = (SocketChannel) sk.channel();
                          sc.read(buffer);
                          buffer.flip();
                          System.out.println(StandardCharsets.UTF_8.decode(buffer));
                      }
                  }
              } catch (IOException e) {
                  e.printStackTrace();
              }
          }
        }
    }
}
