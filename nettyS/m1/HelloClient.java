package com.nettyS.m1;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;

import java.net.InetSocketAddress;

public class HelloClient {
    public static void main(String[] args) throws Exception {
//        创建启动器类
        new Bootstrap()
//                添加组件  服务器中发来数据了，客户端中的selector 就可以触发事件
        .group(new NioEventLoopGroup())
//                客户端选择channel的实现
        .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
//                    在连接建立后就会触该事件,做一个初始化作用
                    @Override
//                    连接建立后才会调用
                    protected void initChannel(NioSocketChannel ch) throws Exception {
//                        需要一个编码器
//                        将字符串转换为ByteBuf  ——  netty 中对ByteBuff 的增强
                        ch.pipeline().addLast(new StringEncoder());

                    }
                })
//                5.连接到服务器
        .connect(new InetSocketAddress("localhost",8080))
//                阻塞方法之间连接建立
                .sync()
//                代表连接对象
                .channel()
//                向服务器发送数据
//                凡是收发数据都会走到处理器内部，去掉处理器内部的方法
                .writeAndFlush("hello world");
    }
}
