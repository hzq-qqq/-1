package com.nettyS.m5.引导类;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.util.CharsetUtil;

public class Server {
    public static void main(String[] args) {
        server();
    }

    private static void server() {
        EventLoopGroup group = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(group)
                .channel(NioServerSocketChannel.class)
                .childHandler(new SimpleChannelInboundHandler<ByteBuf>() {

                    @Override
                    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//                        这里时请求创建了一个代理服务器
                        final Bootstrap bootstrap1 = new Bootstrap();
                        bootstrap1.channel(NioSocketChannel.class)
                                .handler(new SimpleChannelInboundHandler<ByteBuf>() {
                                    @Override
                                    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
                                        System.out.println("received dara");
                                    }
                                })
                                .group(ctx.channel().eventLoop());
                        final ChannelFuture future = bootstrap1.connect("localhost", 8080);
                        future.addListener(new ChannelFutureListener() {
                            @Override
                            public void operationComplete(ChannelFuture future) throws Exception {
                                if (future.isSuccess()){
                                    System.out.println("connect succcess");
                                }else {
                                    System.out.println("connect failed");
                                }
                            }
                        });
                    }
                    @Override
                    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
                        System.out.println("接受到信息： " + msg.toString(CharsetUtil.UTF_8));
                    }
                });
        final ChannelFuture future = bootstrap.bind(8080);
        future.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()){
                    System.out.println("Server bound");
                }else {
                    System.out.println("server bound failed");
                }
            }
        });
    }

    final class ChannelInitializerImpl extends ChannelInitializer<Channel>{

        @Override
        protected void initChannel(Channel ch) throws Exception {
            ch.pipeline().addLast(new HttpClientCodec());
            ch.pipeline().addLast(new HttpObjectAggregator(Integer.MAX_VALUE));
        }
    }


}
