package com.nettyS.练习;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Server {
    public static void main(String[] args) throws InterruptedException {
        final ChannelFuture channelFuture = new ServerBootstrap()
                .group(new NioEventLoopGroup())
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                ByteBuf buf = (ByteBuf) msg;
                                System.out.println(buf.toString(Charset.defaultCharset()));
                                ByteBuf response = ctx.alloc().buffer();
                                response.writeBytes(buf);
                                ctx.writeAndFlush(response);
//                               这里应该需要手动释放buf 和 response 的内存空间，因为默认使用的是直接内存，所以内存的使用不会受到
//                                jvm 内存回收的影响，如果不回收就会一直使用直接内存导致直接内存不够用
                            }
                        });
                    }
                })
                .bind(8080);

    }
}
