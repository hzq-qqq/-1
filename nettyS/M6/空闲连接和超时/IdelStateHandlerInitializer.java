package com.nettyS.M6.空闲连接和超时;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

public class IdelStateHandlerInitializer extends ChannelInitializer<Channel> {
    @Override
    protected void initChannel(Channel ch) throws Exception {
        final ChannelPipeline pipeline = ch.pipeline();
//        空闲时时间为60秒时触发 idelStateEvent事件1
        pipeline.addLast(new IdleStateHandler(0,0,60, TimeUnit.SECONDS));
//         添加一个心跳信息检查  149用到时在看
//        pipeline.addLast(new HeartbeatHander());

    }
}
