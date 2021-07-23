package com.redis.基本指令学习.字符串.计数器;

import redis.clients.jedis.Jedis;

public class Main {
    final static Jedis conn = new Jedis("192.168.139.128", 6379);
    public static void main(String[] args) {
//        incyCount();


    }

    private static void incyCount() {
        String key = "video:playCount:" + 10;
        conn.incr(key);
    }


}
