package com.nettyS.m3;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EventLoopServer {
    public static void main(String[] args) {
//        创建一个独立的EventLooGroup 来处理 执行长时间的任务
         EventLoopGroup group = new DefaultEventLoopGroup();
        new ServerBootstrap()
//                第一个为 boss负责连接  第二个为 worker  负责读写
//                使分工更加明确
                .group(new NioEventLoopGroup(),new NioEventLoopGroup(2))
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast("handler1" , new ChannelInboundHandlerAdapter(){
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                final ByteBuf byteBuf = (ByteBuf) msg;
                                log.debug(byteBuf.toString(CharsetUtil.UTF_8));
//                                将消息传递给下一个handler
                                ctx.fireChannelRead(msg);
                                
                            }
                        }).addLast(group,"handler2" , new ChannelInboundHandlerAdapter(){
                                    @Override
                                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                        final ByteBuf byteBuf = (ByteBuf) msg;
                                        log.debug(byteBuf.toString(CharsetUtil.UTF_8));
                                    }
                                });

                    }
                })
                .bind(8080);

    }
}
