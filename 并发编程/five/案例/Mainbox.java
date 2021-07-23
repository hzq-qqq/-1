package com.并发编程.five.案例;


import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Mainbox {
    private static Integer id = 1;
    private static Map<Integer, GetResult> map = new HashMap<>();

    private synchronized static Integer getId() {
        return id++;
    }

    public static synchronized GetResult remove(int id) {
        return map.remove(id);
    }

    public static synchronized GetResult createGet() {
        GetResult gP = new GetResult(getId());
        map.put(gP.getId(), gP);
        return gP;
    }

    public static synchronized GetResult getResult(int id) {
        return map.get(id);
    }
    public static Set<Integer> getIds() {
        return map.keySet();
    }
}
