package com.redis.基本指令学习.字符串.缓存数据;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class User {
    String username;
    String password;
}
