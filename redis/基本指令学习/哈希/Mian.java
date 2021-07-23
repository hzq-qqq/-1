package com.redis.基本指令学习.哈希;

import com.redis.基本指令学习.字符串.缓存数据.User;
import redis.clients.jedis.Jedis;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Mian {
    final static Jedis conn = new Jedis("192.168.139.128", 6379);
    public static void main(String[] args) {
        String userRedisKey = "user1:" + 1;
         Map<String, String> userMap = conn.hgetAll(userRedisKey);
        User user = null;
         if (userMap.size() > 0){
              user = transferMapToUser(userMap);
         }else {
             System.out.println("从数据库中查询数据");
         }

         System.out.println(user.toString());

    }

    private static User transferMapToUser(Map<String, String> userMap) {
        final Iterator<String> iterator = userMap.keySet().iterator();
        final String key = iterator.next();
        return new User(key,userMap.get(key));
    }
}
