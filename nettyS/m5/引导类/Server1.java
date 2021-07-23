package com.nettyS.m5.引导类;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;

import java.net.InetSocketAddress;

public class Server1 {
    public static void main(String[] args) {
        EventLoopGroup group = new NioEventLoopGroup();
        final ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(group)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitialImpl());
        final ChannelFuture future = bootstrap.bind(new InetSocketAddress("localhost", 8080));
        future.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()){
                    System.out.println("connect success");
                }else {
                    System.out.println("connected failed");
                }
            }
        });
    }

    /**
     * 使用 channelInitialImpl 实现创建多个 handler
     */
    static final class ChannelInitialImpl extends ChannelInitializer<Channel>{

        @Override
        protected void initChannel(Channel ch) throws Exception {
            final ChannelPipeline pipeline = ch.pipeline();
            pipeline.addLast(new HttpClientCodec());
        }
    }
}
