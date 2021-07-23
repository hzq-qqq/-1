package com.网络编程.two;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

public class Main1 {
    public static void main(String[] args) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(32);
        buffer.put("hello world\n I am zhangsan\n ho".getBytes(StandardCharsets.UTF_8));
//        接受数据
        accept(buffer);
//        注意这里需要使用compack（） 压缩剩余未读取的数据
        buffer.put("w are you\n".getBytes(StandardCharsets.UTF_8));
        accept(buffer);
    }

    private static void accept(ByteBuffer buffer) {
//        通过分隔符号来分割数据的
        buffer.flip();
        for (int i = 0; i < buffer.limit(); i++) {
            if (buffer.get(i) == '\n'){
//                将消息存储如新的buffer中
                int lenth = i + 1 - buffer.position();
                ByteBuffer target = ByteBuffer.allocate(lenth);
//              读取数据
                for (int i1 = 0; i1 < lenth; i1++) {
                target.put(buffer.get());
                }
                target.flip();
                System.out.println(StandardCharsets.UTF_8.decode(target));
            }
        }
//        本读取的数据可能是部分数据为读取完整
        buffer.compact();
    }
}
