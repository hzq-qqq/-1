package com.nettyS.m5.ChannelOption使用;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.Future;

public class Server {
    public static void main(String[] args) {
        EventLoopGroup group = new NioEventLoopGroup();
        final AttributeKey<Integer> id = AttributeKey.newInstance("ID");
        final ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(group)
                .channel(NioServerSocketChannel.class)
                .handler(new SimpleChannelInboundHandler<NioSocketChannel>() {
                    @Override
                    protected void channelRead0(ChannelHandlerContext ctx, NioSocketChannel msg) throws Exception {
                        System.out.println("connect ");
                    }

                    @Override
//                    给当前的channel 注册一个
                    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
                        final Integer idValue = ctx.channel().attr(id).get();
                    }
                });
        bootstrap.option(ChannelOption.SO_KEEPALIVE,Boolean.TRUE)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS,5000);
        bootstrap.attr(id,1123456);
        final ChannelFuture future = bootstrap.bind(8080);
        future.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()){
                    System.out.println("connected success");
                }else {
                    System.out.println("connected failed");
                }
            }
        });
//        优雅的关闭netty服务器
        final Future<?> future1 = group.shutdownGracefully();
         future1.syncUninterruptibly();
    }
}
