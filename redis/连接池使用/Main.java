package com.redis.连接池使用;

import com.google.gson.Gson;
import com.redis.client.User;
import redis.clients.jedis.Jedis;

public class Main {
    final static String IP = "LOCALHOST";
    final static int PORT = 6379;
    public static void main(String[] args) {
        final long start = System.currentTimeMillis();
        setUser();
        getUser();
        final long end = System.currentTimeMillis();
        System.out.println("耗费的时间为： " + (end - start));
    }

    private static void getUser() {
        try (Jedis conn = new Jedis(IP, PORT)) {
            final String value = conn.get("user");
            final User user = new Gson().fromJson(value, User.class);
            System.out.println(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void setUser() {
        try (Jedis conn = new Jedis(IP, PORT)) {
            User user = new User("zhangsan", "123456");
            final String value = new Gson().toJson(user);
            conn.set("user", value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
