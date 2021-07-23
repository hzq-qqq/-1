package com.nettyS.协议的解析于设计.Sharable注解使用;

import com.nettyS.协议的解析于设计.Sharable注解使用.可共享的编解码器.MessageToMessage;
import com.nettyS.协议的解析于设计.自定义协议要素.MessageCode;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ChartServer {
    public static void main(String[] args) {
        final NioEventLoopGroup boss = new NioEventLoopGroup();
        final NioEventLoopGroup worker = new NioEventLoopGroup();
        final ServerBootstrap bootstrap = new ServerBootstrap();
        try {
            final LoggingHandler loggindHandel = new LoggingHandler();
            final MessageToMessage messageCode = new MessageToMessage();
            bootstrap.group(boss, worker);
            bootstrap.channel(NioServerSocketChannel.class);
            bootstrap.childHandler(new ChannelInitializer<NioSocketChannel>() {
                @Override
                protected void initChannel(NioSocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(1024, 12, 4, 4, 0));
                    ch.pipeline().addLast(loggindHandel);
                    ch.pipeline().addLast(messageCode);
                }
            });
            final ChannelFuture channelFuture = bootstrap.bind(8080).sync();
            channelFuture.channel().closeFuture().sync();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            worker.shutdownGracefully();
            boss.shutdownGracefully();
        }
    }
}
