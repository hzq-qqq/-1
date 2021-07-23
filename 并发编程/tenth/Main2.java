package com.并发编程.tenth;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * concurrentHashMap 错误的使用例子
 */
public class Main2 {

    static ConcurrentHashMap<String, List<String>>map = new ConcurrentHashMap<>();
    public static void main(String[] args) {
        Thread t1 = new Thread(()->{
            List<String>list1 = new CopyOnWriteArrayList<>();
            list1.add("device1");
            list1.add("device11");
            map.putIfAbsent("topic1",list1);
            System.out.println(map);
        });
        Thread t2 = new Thread(()->{
            List<String>list1 = new CopyOnWriteArrayList<>();
            list1.add("device2");
            list1.add("device22");
            map.putIfAbsent("topic1",list1);
            System.out.println(map);
        });
        Thread t3 = new Thread(()->{
            List<String>list1 = new CopyOnWriteArrayList<>();
            list1.add("device3");
            list1.add("device33");
            map.putIfAbsent("topic2",list1);
            System.out.println(map);
        });

        t1.start();
        t2.start();
        t3.start();
    }

}
