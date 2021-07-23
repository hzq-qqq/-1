package com.nettyS.粘包半包Netty处理.Ltc解码器;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.nio.charset.StandardCharsets;

public class TestLengthFieldDecoder {
    public static void main(String[] args) {
        EmbeddedChannel ec = new EmbeddedChannel(
                new LengthFieldBasedFrameDecoder(1024,0,4,0,0),
                new LoggingHandler(LogLevel.DEBUG)
        );

//        4 字节长度
        final ByteBuf byteBuf = ByteBufAllocator.DEFAULT.buffer();
        send(byteBuf,"hello world");
        send(byteBuf,"hi");
        send(byteBuf,"how do you do");
          ec.writeInbound(byteBuf);
    }

    private static void send(ByteBuf byteBuf ,String content) {
        final byte[] bytes = content.getBytes(StandardCharsets.UTF_8);
        final int length = bytes.length;
//        先写长度 再写内容
        byteBuf.writeInt(length);
//        加上一字节的版本号
        byteBuf.writeByte(1);
        byteBuf.writeBytes(bytes);
    }
}
