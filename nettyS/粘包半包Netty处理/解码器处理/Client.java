package com.nettyS.粘包半包Netty处理.解码器处理;

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

@Slf4j
/**
 * netty 中半包 和粘包的展示,只要是用tcp 来处理都会右半包和粘包的问题
 *
 *  解决方案：
 *      1.短链接方案
 *              —— 人为的制造消息边界  —— 不能解决半包问题 —— 当服务器的接受缓冲区小于一次发送的数据时，就会出现半包问题
 *              —— 效率比较低，
 *      2. 定长解码器 —— 实现约定客户端和服务器之间每次传输数据的大小
 *      3.
 */
public class Client {
    public static void main(String[] args) {

            send();

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
                                        final ByteBuf buf = ctx.alloc().buffer(16);
                                    for (int i = 0; i < 10; i++) {
                                        buf.writeBytes(new byte[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14});
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
}
