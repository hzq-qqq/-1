package com.nettyS.M6.基于长度的解码器;

import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.util.CharsetUtil;

import java.io.File;
import java.io.FileInputStream;

public class LengthBasedInitizer extends ChannelInitializer<Channel> {
    @Override
    protected void initChannel(Channel ch) throws Exception {
        final ChannelPipeline pipeline = ch.pipeline();
//        表示最接受数据的最大的大小是64kb，然后表示帧数据大小的属性的偏移量为0，大小为8字节
        pipeline.addLast(new LengthFieldBasedFrameDecoder(64 * 1024,0,8));
        pipeline.addLast(new FrameHandler());
    }

    private static class FrameHandler extends SimpleChannelInboundHandler<ByteBuf>{
        @Override
        protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
//            处理读取事件
            System.out.println(msg.toString(CharsetUtil.UTF_8));
//           写入大数据的情况

        }
    }
}
