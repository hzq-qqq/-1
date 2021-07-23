package com.nettyS.m4;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.CompositeByteBuf;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.nio.channels.Channel;
import java.nio.charset.StandardCharsets;

import static io.netty.buffer.ByteBufUtil.appendPrettyHexDump;
import static io.netty.util.internal.StringUtil.NEWLINE;

@Slf4j
public class TestByteBuf {
    public static void main(String[] args) {
//        ByteBuf 的创建
        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();
        System.out.println(buf.getClass());
       log(buf);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 32; i++) {
            sb.append("a");
        }
        buf.writeBytes(sb.toString().getBytes(StandardCharsets.UTF_8));
        for (int i = 0; i < 1; i++) {
            buf.readInt();
        }
       log(buf);
    }

    @Test
    /**
     * 直接内存 VS 对内存
     * netty 中默认使用的是直接内存
     *
     */
    public void test(){
     ByteBuf byteBuf = ByteBufAllocator.DEFAULT.buffer();
     byteBuf.writeBytes(new byte[]{1,2,3,4,5,6});
     ByteBuf byteBuf1 = ByteBufAllocator.DEFAULT.buffer();
     byteBuf1.writeBytes(new byte[]{7,8,9,10,11});

     ByteBuf buf3 = ByteBufAllocator.DEFAULT.buffer();
//     buf3.writeBytes(byteBuf).writeBytes(byteBuf1);
        final CompositeByteBuf compositeByteBuf = ByteBufAllocator.DEFAULT.compositeBuffer();
//        注意这里需要加上参数true 表示 自动增长读指针，否者无法写入数据
        compositeByteBuf.addComponents(true,byteBuf,byteBuf1);
//        防止意外被删除int
        compositeByteBuf.retain();
        log(compositeByteBuf);


    }

    public static void log(ByteBuf buffer) {
        int length = buffer.readableBytes();
        int rows = length / 16 + (length % 15 == 0 ? 0 : 1) + 4;
        StringBuilder buf = new StringBuilder(rows * 80 * 2)
                .append("read index:").append(buffer.readerIndex())
                .append(" write index:").append(buffer.writerIndex())
                .append(" capacity:").append(buffer.capacity())
                .append(NEWLINE);
        appendPrettyHexDump(buf, buffer);
        System.out.println(buf.toString());
    }
}
