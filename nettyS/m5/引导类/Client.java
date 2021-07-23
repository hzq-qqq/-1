package com.nettyS.m5.引导类;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;

import java.nio.charset.StandardCharsets;

public class Client {
    public static void main(String[] args) {
        client();
    }

    private static void client() {
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new SimpleChannelInboundHandler<ByteBuf>() {
                    @Override
                    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
                        System.out.println(msg.toString(CharsetUtil.UTF_8));
                        System.out.println("received data");
                    }
                });
        final ChannelFuture channelFuture = bootstrap.connect("localhost", 8080);
        channelFuture.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()){
                    System.out.println("连接成功");
                }else {
                    System.out.println("连接失败");
                }
            }
        });
    }


}
