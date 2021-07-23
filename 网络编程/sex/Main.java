package com.网络编程.sex;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * boss 线程，专门用于监听accent事件
 */
@Slf4j
public class Main {
    public static void main(String[] args) throws IOException {
     Thread.currentThread().setName("boss");
         ServerSocketChannel ssc = ServerSocketChannel.open();
         ssc.configureBlocking(false);
         Selector boss = Selector.open();
         SelectionKey bosskey = ssc.register(boss, 0, null);
        bosskey.interestOps(SelectionKey.OP_ACCEPT);
        ssc.bind(new InetSocketAddress(9543));
//        床架固定数量的woker
//        优化将worker 变为多个
//        动态获取 当前机器的可用的cpu核心数，一般是 有多少个核心数，就使用多少个worker
         Worker[] workers = new Worker[2];
        for (int i = 0; i < workers.length; i++) {
            workers[i] = new Worker("worker - " + i);
        }

        AtomicInteger index = new AtomicInteger();
        while (true){
            boss.select();
             Iterator<SelectionKey> it = boss.selectedKeys().iterator();
            while (it.hasNext())
            {
                 SelectionKey key = it.next();
                 it.remove();
                 if (key.isAcceptable()){
                      SocketChannel sc = ssc.accept();
                     sc.configureBlocking(false);
                      log.debug("before connected....{}",sc.getRemoteAddress());
                     //                     创建一个单独的worker 来处理这个线程的读写事件
//                     两个不同的线程使用了同一个selector ，不同线程对selector使用就会相互影响  woker中的selecetor.select() 会
//                     问题： woker 中 selector.select() 方法会阻塞当前的这个线程的register的执行
//                     解决方法：
//                          将这两个使用的了selector 的方法 在同一个线程内同步执行

//                     让线程轮流的使用 worker —— 第一个 gei 0 ， 第二个 给 1 第三个给2
                     workers[index.getAndIncrement() % workers.length].register(sc);

                     log.debug("after connected....{}",sc.getRemoteAddress());
                 }
            }
        }
    }

    /**
     * 检测读写事件 客户单独拥有的
     */
    @Slf4j
    static class Worker implements Runnable{
        private Thread thread;
        private Selector selector;
        private String name;
//        还未初始化
        private volatile boolean start = false;
//        使用队列来解决两个线程之间通信的问题
        private ConcurrentLinkedDeque<Runnable> queue = new ConcurrentLinkedDeque<>();

        public Worker(String nake){
            this.name = nake;
        }
//        初始化线程和selector
        public void register(SocketChannel sc) throws IOException {
            if (!start) {
                thread = new Thread(this, name);
                selector = Selector.open();
                thread.start();
                start = true;
            }
//            向队列添加任务 当添加任务完成之后就唤醒selector，
            queue.add(()->{
                try {
                    sc.register(selector,SelectionKey.OP_READ,null);
                } catch (ClosedChannelException e) {
                    e.printStackTrace();
                }
            });
            selector.wakeup(); // 唤醒 selector;

        }

        @Override
        public void run() {
            while (true) {
                try {
//                    第一次 监听事件阻塞 -> 在netty中使用了类似的方法
                    final Runnable task = queue.poll();
                    if (task != null){
                        task.run();
                    }
                    selector.select();
                    Iterator<SelectionKey> it = selector.selectedKeys().iterator();
                     while (it.hasNext()){
                          SelectionKey selectionKey = it.next();
                          it.remove();
                          if (selectionKey.isReadable()){
                               ByteBuffer buffer = ByteBuffer.allocate(16);
                               SocketChannel sc = (SocketChannel) selectionKey.channel();
                               log.debug(this.name);
                              sc.read(buffer);
                              buffer.flip();
                              debugAll(buffer);
                          }
                     }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }

        private void debugAll(ByteBuffer buffer) {
            System.out.println(StandardCharsets.UTF_8.decode(buffer));
        }
    }
}
