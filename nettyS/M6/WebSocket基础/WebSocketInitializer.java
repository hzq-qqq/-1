package com.nettyS.M6.WebSocket基础;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;

public class WebSocketInitializer extends ChannelInitializer<Channel> {
    @Override
    protected void initChannel(Channel ch) throws Exception {
        final ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(
                new HttpServerCodec(),
                new HttpObjectAggregator(65535),
                new WebSocketServerProtocolHandler("/websocket")
        );
    }
}
