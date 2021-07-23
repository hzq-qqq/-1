package com.nettyS.协议的解析于设计.自定义协议要素;

import com.nettyS.协议的解析于设计.自定义协议要素.message.LoginRequestMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class TestMessageCodec {
    public static void main(String[] args) throws Exception{

//        问题分析：
//           不同的的eventLoop 使用同一个帧解码器，可能出现拼接两个不同的信息的半包，然后被误认为是一条完整的信息被发送
//        可以发现在 LengthFieldBasedFrameDecoder 中没有使用@Sharable 标注
        final LengthFieldBasedFrameDecoder filHandler = new LengthFieldBasedFrameDecoder(1024, 12, 4, 4, 0);
        final LoggingHandler loginHandler = new LoggingHandler();
        final EmbeddedChannel channel = new EmbeddedChannel(
//                配置帧解码器
                loginHandler,
                filHandler,
                new MessageCode());
        final LoginRequestMessage message = new LoginRequestMessage("zhangsan", "123", "张三");
//        channel.writeOutbound(message);

//        decoding 测试
        ByteBuf buf  = ByteBufAllocator.DEFAULT.buffer();
        new MessageCode().encode(null,message,buf);

//        测试半包粘包现象
        final ByteBuf slice = buf.slice(0,100);
        final ByteBuf slice1 = buf.slice(100,buf.readableBytes() - 100);


//        注意这里有一个小坑，在channel 将slice 写入栈处理器中时，会将当前的slice 释放掉， 为了测试半包现象，
//        不能让其指向的内存被释放掉，所以需要将   slice.retain();  使引用计数器的值加一
        channel.writeInbound(slice);
          slice.retain();
        channel.writeInbound(slice1);
    }

    /**
     * 线程安全的handler —— 多个eventLoop 使用同一个handler
     * @sharable 注解标注的handler 就是线程安全的
     */
}
