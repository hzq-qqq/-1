package com.redis.基本指令学习.字符串.缓存数据;

import com.google.gson.Gson;
import redis.clients.jedis.Jedis;

public class Main {
    final static Jedis conn = new Jedis("192.168.139.128", 6379);
    public static void main(String[] args) {
//        setValue(10);
//        getVlaue(10);

    }

    private static void getVlaue(int id) {
        String userRedisKey = "user:Info:" + id;
        String value = conn.get(userRedisKey);
//        反序列化得到数据
        User user = new Gson().fromJson(value,User.class);
        System.out.println(user.toString());
    }

    private static void setValue(int id) {
        String userRedisKey = "user:Info:" + id;
        User user = new User("张三","123456");
//        序列化数据，然后存储到redis中
        final String json = new Gson().toJson(user);
        conn.set(userRedisKey,json);
    }

}
