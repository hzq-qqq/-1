package com.nettyS.M6;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.TooLongFrameException;

import java.util.List;

public class SafeByteToMessageDecoder extends ByteToMessageDecoder {
    private final static int MAX_FRAME_ZISE = 1024;
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        final int bytes = in.readableBytes();
        if (bytes >= MAX_FRAME_ZISE){
//            跳过所有的可读字节数，抛出异常返回
            in.skipBytes(bytes);
            throw new TooLongFrameException("Frame too big");
        }
    }
}
