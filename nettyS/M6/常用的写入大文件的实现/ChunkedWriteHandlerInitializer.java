package com.nettyS.M6.常用的写入大文件的实现;

import io.netty.channel.*;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.stream.ChunkedStream;
import io.netty.handler.stream.ChunkedWriteHandler;

import java.io.File;
import java.io.FileInputStream;

public class ChunkedWriteHandlerInitializer extends ChannelInitializer<Channel> {
    private final File file;
    private final SslContext context;

    public ChunkedWriteHandlerInitializer(File file, SslContext context) {
        this.file = file;
        this.context = context;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        final ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new SslHandler(context.newEngine(ch.alloc())));
//        这里使用的是netty默认的实现来实现文件的传输的
        pipeline.addLast(new ChunkedWriteHandler());
        pipeline.addLast(new WriteStreanHandler());
    }

    private  class WriteStreanHandler extends ChannelInboundHandlerAdapter {
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            super.channelActive(ctx);
            ctx.writeAndFlush(new ChunkedStream(new FileInputStream(file)));

        }
    }
}
