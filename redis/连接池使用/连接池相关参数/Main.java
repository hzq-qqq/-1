package com.redis.连接池使用.连接池相关参数;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Main {
    final static String IP = "LOCALHOST";
    final static int PORT = 6379;
    final static GenericObjectPoolConfig<Jedis> FONFIG = new GenericObjectPoolConfig<Jedis>();
    final static JedisPool JEDIS_POOL = new JedisPool(FONFIG,IP,PORT);
    static {
//        设置最大连接数量
        FONFIG.setMaxTotal(GenericObjectPoolConfig.DEFAULT_MAX_TOTAL * 5);
//        设置最大空闲连接数
        FONFIG.setMaxIdle(GenericObjectPoolConfig.DEFAULT_MAX_IDLE * 3);
//        最小空闲连接数量
        FONFIG.setMinIdle(GenericObjectPoolConfig.DEFAULT_MIN_IDLE * 2);
//        开启jmx 功能
        FONFIG.setJmxEnabled(true);
//        设置连接池没有连接到客户端的最大等待时间
        FONFIG.setMaxWaitMillis(3000);

//        设置连接的最小空闲时间 3 分钟
        FONFIG.setMinEvictableIdleTimeMillis(1800000);

//        向连接池借用连接时做空闲检测
        FONFIG.setTestWhileIdle(true);

//        空闲检测周期
        FONFIG.setTimeBetweenEvictionRunsMillis(30);

//        池中没有连接时，直接抛出异常
        FONFIG.setBlockWhenExhausted(true);

    }
    public static void main(String[] args) throws Exception{
//        setValue();
//        dekValue();
////        模拟超时断开连接
//        outTimer();

//        overConn();


    }

    private static void overConn() {
        for (int i = 0; i < 9; i++) {
            final Jedis conn = JEDIS_POOL.getResource();
        }
    }

    private static void outTimer() {
        try (   final Jedis conn = new Jedis(IP, PORT);){
            String key = "user";
            System.out.println(conn.get(key));
            TimeUnit.SECONDS.sleep(31);
            System.out.println(conn.get(key));
            TimeUnit.SECONDS.sleep(5);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void dekValue() {
        //        使用pipeline 实现批量删除
        long start = System.currentTimeMillis();
        final Jedis conn = JEDIS_POOL.getResource();
        final Pipeline pipeline = conn.pipelined();
        for (int i = 0; i < 100; i++) {
            pipeline.del("value: " + i);
//            conn.del("value: " + i);
        }
        pipeline.sync();
        long end = System.currentTimeMillis();
        System.out.println("执行时间为：" + (end - start));
    }

    private static void setValue() {
        final Jedis conn = JEDIS_POOL.getResource();
        final Pipeline pipeline = conn.pipelined();
        Map<String,String> map = new HashMap<>();
        for (int i = 0; i < 100; i++) {
            map.put("value: " + i,String.valueOf(i));
        }
        for (String key : map.keySet()) {
            final String value = map.get(key);
            pipeline.set(key, value);
        }
        pipeline.sync();
    }


}
