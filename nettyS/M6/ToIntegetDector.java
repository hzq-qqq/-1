package com.nettyS.M6;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class ToIntegetDector extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() >=4 ){
//            注意这里的byteBuf中的数据，一旦被解码或者编码之后就会被立即自动的释放掉
            out.add(in.readInt());
        }
    }
}
