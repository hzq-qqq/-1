package com.nettyS.M6.编解码器;

import io.netty.channel.CombinedChannelDuplexHandler;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToByteEncoder;

public class CombineByteCharCodec extends CombinedChannelDuplexHandler<ByteToMessageDecoder, MessageToByteEncoder<Character>> {
    public CombineByteCharCodec() {
    }

    public CombineByteCharCodec(ByteToMessageDecoder inboundHandler, MessageToByteEncoder<Character> outboundHandler) {
        super(inboundHandler, outboundHandler);
    }
}
