package com.nettyS.协议的解析于设计.自定义协议要素.message;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class GroupChatResponseMessage extends AbstractResponseMessage {
    private String from;
    private String content;

    public GroupChatResponseMessage(boolean success, String reason) {
        super(success, reason);
    }

    public GroupChatResponseMessage(String from, String content) {
        this.from = from;
        this.content = content;
    }
    @Override
    public int getMessageType() {
        return GroupChatResponseMessage;
    }
}
