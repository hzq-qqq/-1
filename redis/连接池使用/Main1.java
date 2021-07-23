package com.redis.连接池使用;

import com.google.gson.Gson;
import com.redis.client.User;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * 可以发现性能相比较于直接连接redis  执行效率提升了2。6倍
 */
public class Main1 {
    final static String IP = "LOCALHOST";
    final static int PORT = 6379;
    final static GenericObjectPoolConfig<Jedis> FONFIG = new GenericObjectPoolConfig<Jedis>();
    final static JedisPool JEDIS_POOL = new JedisPool(FONFIG,IP,PORT);
    public static void main(String[] args) {
        final long start = System.currentTimeMillis();
        setUser();
        getUser();
        final long end = System.currentTimeMillis();
        System.out.println("耗费的时间为： " + (end - start));
    }

    private static void getUser() {
        try (Jedis conn = JEDIS_POOL.getResource()) {
            conn.clientSetname("test_name");
            final String value = conn.get("user");
            final User user = new Gson().fromJson(value, User.class);
            System.out.println(user);
            System.out.println(conn.clientList());
            System.out.println(conn.info());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void setUser() {
        try (Jedis conn = JEDIS_POOL.getResource()) {
            User user = new User("zhangsan", "123456");
            final String value = new Gson().toJson(user);
            conn.set("user", value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
