package com.nettyS.粘包半包Netty处理.分隔符来解决;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.Random;

@Slf4j
/**
 * netty 中半包 和粘包的展示,只要是用tcp 来处理都会右半包和粘包的问题
 *
 *  解决方案：
 *      1.短链接方案
 *              —— 人为的制造消息边界  —— 不能解决半包问题 —— 当服务器的接受缓冲区小于一次发送的数据时，就会出现半包问题
 *              —— 效率比较低，
 *      2.
 */
public class Client {
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            send();
        }
        System.out.println("finish......");
    }

    private static void send() {
        NioEventLoopGroup worker = new NioEventLoopGroup();
        try {
            final ChannelFuture future = new Bootstrap()
                    .group(worker)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new LoggingHandler(LogLevel.DEBUG));
                            ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                                @Override
                                public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                         ByteBuf buf = ctx.alloc().buffer(16);
                                         Random r = new Random();
                                         char c = '0';
                                       for (int i = 0; i < 10; i++) {
                                           final StringBuilder sb = makeString(c, r.nextInt(256) + 1);
                                           c++;
                                            buf.writeBytes(sb.toString().getBytes(StandardCharsets.UTF_8));
                                        }
                                          ctx.writeAndFlush(buf);
                                }
                            });
                        }
                    })
                    .connect("localhost", 8080).sync();
            future.channel().closeFuture().sync();
        }catch (Exception e){
            log.debug("error client");
        }finally {
            worker.shutdownGracefully();
        }
    }
    public static StringBuilder makeString(char c , int len){
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            sb.append(c);
        }
        sb.append("\n");
        return sb;
    }
}
