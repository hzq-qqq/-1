package com.nettyS.协议的解析于设计.自定义协议要素;

import com.nettyS.协议的解析于设计.自定义协议要素.message.Message;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

/**
 *  Byte 和自定义类型之间的转换
 */
@Slf4j
@ChannelHandler.Sharable
public class MessageCode extends ByteToMessageCodec<Message> {

    /**
     * 对信息进行编码
     * @param ctx
     * @param msg
     * @param out
     * @throws Exception
     */
    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out) throws Exception {
//        四字节魔数
       out.writeBytes(new byte[]{1,2,3,4});
//        一字节的版本
       out.writeByte(1);
//       序列化算法   为了扩展性比较好，可以支持多种
//        一字节的序列化方式  jdk: 0  json : 1
        out.writeByte(0);
//        指令类型  登陆 退出 注册， 单聊 ，群聊 ，跟业务相关
        out.writeByte(msg.getMessageType());
//         无意义对其补充
        out.writeByte(0xff);
        //        请求序号 4 字节
        out.writeInt(msg.getSequenceId());
//        获取内容的字节数组
        ByteOutputStream bos = new ByteOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(msg);
        final byte[] bytes = bos.toByteArray();
        //       正文长度
        out.writeInt(bytes.length);
//        写入内容
        out.writeBytes(bytes);
    }

    @Override
    /**
     * 注意这里需要将解析出来的信息存储再 List<Object> out对象中 ，给下一个和handler 使用
     */
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        final int magNum = in.readInt();
        final byte version = in.readByte();
        final byte serializerType = in.readByte();
        final byte messageType = in.readByte();
        final int sequenceId = in.readInt();
        in.readByte();
        final int length = in.readInt();
        byte[] bytes = new byte[length];
        in.readBytes(bytes,0,length);
//        将读取到的内容反序列化为对象
            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
            Message message = (Message) ois.readObject();
        log.debug("{},{},{},{},{},{}",magNum,version,serializerType,messageType,length);
        out.add(message);
    }
}
