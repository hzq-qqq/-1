package com.nettyS.M6.协议;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.*;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslHandler;

import javax.net.ssl.SSLEngine;

public class HtttpHandler extends ChannelInitializer<Channel> {
    private final boolean client;
    private final SslContext context;

    public HtttpHandler(boolean client, SslContext context) {
        this.client = client;
        this.context = context;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        final ChannelPipeline pipeline = ch.pipeline();
        final SSLEngine engine = context.newEngine(ch.alloc());
        if (client){
//           添加http 协议支持 ，用户处理http 协议的消息
            pipeline.addLast("decoder",new HttpResponseDecoder());
            pipeline.addLast("encoder",new HttpRequestEncoder());
//            聚合http协议的各个部分的消息 —— 因为http协议的消息是有多个不同的部分组成
//            因为需要据和多个消息，所以需要使用缓存来存储各个部分消息的，当可以组成一个httpObject 时，才转发给下一个handler
            pipeline.addLast("codec",new HttpClientCodec());
//            提供加密功能
            pipeline.addLast("Ssl",new SslHandler(engine));

//            压缩httpObject 数据
            pipeline.addLast("compressor",new HttpContentCompressor());
        }else {
            pipeline.addLast("decoder",new HttpResponseEncoder());
            pipeline.addLast("encoder",new HttpRequestDecoder());
            pipeline.addLast("codec",new HttpServerCodec());
            pipeline.addLast("compressor",new HttpContentCompressor());
            pipeline.addLast("Ssl",new SslHandler(engine));
        }
    }
}
