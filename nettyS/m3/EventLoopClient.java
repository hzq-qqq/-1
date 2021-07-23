package com.nettyS.m3;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.Scanner;

@Slf4j
public class EventLoopClient {
    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();
          ChannelFuture channelFuture = new Bootstrap()
                .group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new StringEncoder());
                        ch.pipeline().addLast(new LoggingHandler(LogLevel.DEBUG));
                    }
                })
//                  异步非阻塞 main 发起调用  正真执行连接的使 另一个线程—— Nio线程
                .connect(new InetSocketAddress("localhost", 8080));

//          阻塞当前线程（main） 知道Nio 线程连接建立完毕
//        方法 1. 同步处理
//        channelFuture.sync();
//        final Channel channel = channelFuture.channel();
//        log.debug("{}",channel);
//        channel.writeAndFlush("hello world ");

////        2 异步处理结果 —— addlistener  _ 建立连接完成之后 会执行该方法  注意该方法是有NIO线程来执行
//          channelFuture.addListener(new ChannelFutureListener() {
//              @Override
//              public void operationComplete(ChannelFuture future) throws Exception {
//                  final Channel channel = future.channel();
//                  log.debug("{}",channel);
//                  channel.writeAndFlush("hello world");
//              }
//          });

        final Channel channel = channelFuture.sync().channel();
        log.debug("{}",channel);
        new Thread(()->{
            Scanner sin = new Scanner(System.in);
            while (true){
                final String s = sin.nextLine();
                if ("q".equals(s)){
                    channel.close();  // 注意这是异步操作 ，所以 处理关闭连接之后的操作不能够在这里处理
                    break;
                }
                channel.writeAndFlush(s);
            }
        },"input").start();

//         法1.CloseFuture 对象来处理关闭
        final ChannelFuture closeFuture = channel.closeFuture();
//        log.debug("wait close");
//        同步控制
//        closeFuture.sync();
//        log.debug("处理关闭连接之后的工作");
//        法2   使用listener

        closeFuture.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                log.debug("处理关闭连接之后的善后工作");
//                优雅的是客户端停下来
                group.shutdownGracefully();
            }
        });

    }
}
