package com.nettyS.协议的解析于设计.Http协议编辑码器;

import com.sun.corba.se.internal.CosNaming.BootstrapServer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_LENGTH;


@Slf4j
public class TestHttp {
    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();
        final ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(boss,worker);
        serverBootstrap.channel(NioServerSocketChannel.class);
        serverBootstrap.childHandler(new ChannelInitializer<NioSocketChannel>() {
            @Override
            protected void initChannel(NioSocketChannel ch) throws Exception {
                ch.pipeline().addLast(new LoggingHandler(LogLevel.DEBUG));
                ch.pipeline().addLast(new HttpServerCodec());
                ch.pipeline().addLast(new SimpleChannelInboundHandler<HttpRequest>(){

                    @Override
                    protected void channelRead0(ChannelHandlerContext ctx, HttpRequest msg) throws Exception {
                        log.debug(msg.uri());
                        DefaultFullHttpResponse response  = new DefaultFullHttpResponse(msg.protocolVersion(),HttpResponseStatus.OK);
                        final byte[] bytes = "<h1>hello world</h1>".getBytes(StandardCharsets.UTF_8);

                        response.headers().setInt(CONTENT_LENGTH,bytes.length);
                        response.content().writeBytes(bytes);
                        ctx.writeAndFlush(response);

                    }
                });
            }
        });
        final ChannelFuture channelFuture = serverBootstrap.bind(8080).sync();
        channelFuture.channel().closeFuture().sync();
        worker.shutdownGracefully();
        boss.shutdownGracefully();
    }
}
