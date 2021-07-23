package com.nettyS.m5;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DiscardHandler1 extends SimpleChannelInboundHandler {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
//        主要SimpleChannelInboundHandler 会自动的释放资源,所以不能使用其来存储任何的引用来使用，都会失效
      log.debug("msg {}",msg);
    }
}
