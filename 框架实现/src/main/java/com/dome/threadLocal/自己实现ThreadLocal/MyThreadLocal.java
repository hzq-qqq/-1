package com.dome.threadLocal.自己实现ThreadLocal;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MyThreadLocal <T>{
//    同步map ？  为什么？
    private Map<Thread,T> container = Collections.synchronizedMap(new HashMap<Thread, T>());

    public void set(T value){
        container.put(Thread.currentThread(),value);
    }
    public T get(){
        Thread thread = Thread.currentThread();
        T value = container.get(thread);
//        value 不存在 ，且 还未初始化线程到容器中
        if (value == null && !container.containsKey(thread)){
            value = initialValue();
            container.put(thread,value);
        }
        return value;
    }

    /**
     * 保护的权限修饰符  表示让程序员自己这设置初始值
     * @return
     */
    protected T initialValue() {
        return null;
    }

    public void remove(){
        container.remove(Thread.currentThread());
    }
}
