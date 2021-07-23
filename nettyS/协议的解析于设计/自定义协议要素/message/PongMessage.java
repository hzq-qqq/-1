package com.nettyS.协议的解析于设计.自定义协议要素.message;

public class PongMessage extends Message {
    @Override
    public int getMessageType() {
        return PongMessage;
    }
}
