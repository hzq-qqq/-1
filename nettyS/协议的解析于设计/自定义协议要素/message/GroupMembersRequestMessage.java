package com.nettyS.协议的解析于设计.自定义协议要素.message;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class GroupMembersRequestMessage extends Message {
    private String groupName;

    public GroupMembersRequestMessage(String groupName) {
        this.groupName = groupName;
    }

    @Override
    public int getMessageType() {
        return GroupMembersRequestMessage;
    }
}
