package com.nettyS.m5.单元测试;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.CharsetUtil;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * 自定义一个帧解码器 固定接受的字节数
 */
public class FixedLengthFrameDeco extends ByteToMessageDecoder {
    private final int frameLength;

    public FixedLengthFrameDeco(int frameLength) {
        if (frameLength <= 0){
            throw new IllegalArgumentException();
        }
        this.frameLength = frameLength;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
//          自定义每次读取数据字节数
        while (in.readableBytes() >= frameLength){
            final ByteBuf byteBuf = in.readBytes(frameLength);
            out.add(byteBuf);
        }
    }

    public static void main(String[] args) {
        final ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer();
        for (int i = 0; i < 9; i++) {
            buffer.writeByte(i);
        }
        final ByteBuf input = buffer.duplicate();
        final EmbeddedChannel channel = new EmbeddedChannel(new FixedLengthFrameDeco(9));
        channel.writeInbound(input);

        ByteBuf read = channel.readInbound();

        System.out.println(read.toString(CharsetUtil.UTF_8));
        ByteBuf read1 = channel.readInbound();

        System.out.println(read.toString(CharsetUtil.UTF_8));

    }

}
