package com.nettyS.ByteBufStudy;

import com.nettyS.m4.TestByteBuf;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.util.ByteProcessor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {
    public static void main(String[] args) {
        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.buffer();
        byteBuf.writeBytes(new byte[]{1,2,3,4,5,6});
//        for (int i = 0; i < byteBuf.writerIndex() - 3; i++) {
//            System.out.println(byteBuf.readByte());
//        }
//        System.out.println("=================");
//        byteBuf.discardReadBytes();
//        System.out.println(byteBuf.readerIndex());
//        System.out.println(byteBuf.writerIndex());
//        System.out.println("===============");
//        for (int i = 0; i < byteBuf.writerIndex(); i++) {
//            System.out.println(byteBuf.getByte(i));
//        }
//        while (byteBuf.isReadable()){
//            System.out.println(byteBuf.readByte());
//        }
//        while (byteBuf.writableBytes() >= 4){
//            byteBuf.writeBytes(new byte[]{1,2,3,4});
//        }
//        while (byteBuf.isReadable()){
//            System.out.println(byteBuf.readByte());
//        }


//        缩索引管理
//        System.out.println("=============");
//         byteBuf.markReaderIndex();
//         while (byteBuf.isReadable()){
//             System.out.println(byteBuf.readByte());
//         }
//         byteBuf.resetReaderIndex();
//        while (byteBuf.isReadable()){
//            System.out.println(byteBuf.readByte());
//        }

//        byteBuf.markWriterIndex();
//        while (byteBuf.writableBytes() >= 230){
//            byteBuf.writeBytes(new byte[] {1,1,12,3,3});
//        }
//        System.out.println(byteBuf.writerIndex());
//
//        byteBuf.resetWriterIndex();
//        System.out.println("======");
//        System.out.println(byteBuf.writerIndex());


//       派生换缓冲区

//        copy 复制真实的数据

//        切片  —— 切片维护了自己独立的 read 和write
//        final ByteBuf slice = byteBuf.slice();
//        slice.setByte(0,10);
//        while (slice.isReadable()){
//            System.out.println(slice.readByte());
//        }
//        while (byteBuf.isReadable()){
//            System.out.println(byteBuf.readByte());
//        }

//        while (byteBuf.isWritable()){
//            byteBuf.writeBytes(new byte[]{1,2,3,4,5});
//        }
//        while (byteBuf.isReadable()){
//            System.out.println(byteBuf.readByte());
//        }

        System.out.println("===================");
//       ByteBufUtil.hexDump(byteBuf);

        final ByteBuf buffer = Unpooled.buffer();
        System.out.println(buffer.getClass());

        System.out.println(buffer.refCnt());
//        引用技术器的值加1
        buffer.retain();
        System.out.println(buffer.refCnt());
//        引用计数器的值减一 如果为 0则释放buffer
        buffer.release();
        System.out.println(buffer.refCnt());
    }
}
