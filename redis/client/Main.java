package com.redis.client;

import com.google.gson.Gson;
import redis.clients.jedis.Jedis;

import java.nio.charset.StandardCharsets;

public class Main {
    final static String IP = "LOCALHOST";
    final static int PORT = 6379;
    public static void main(String[] args) {
        setUser();
        getUser();
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
