package com.handFirst.dlms.lanhanshi;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

public class Lhanshi {
    private static boolean flag = false;
    private Lhanshi() {

        synchronized (Lhanshi.class) {
            if (flag == false) {
                flag = true;
                System.out.println(Thread.currentThread().getName() + " ok");
            } else {
                System.out.println(Thread.currentThread().getName() + " ok");
                throw new RuntimeException("不要以反射来破环单例模式");
            }
        }
    }
    private volatile static Lhanshi list;

    public static Lhanshi getInstance(){
        if (list == null){
            synchronized (Lhanshi.class){
                if (list == null){
                    list = new Lhanshi();
                }
            }
        }
        return list;
    }

    public static void main(String[] args) throws Exception {
        Field flag = Lhanshi.class.getDeclaredField("flag");
        flag.setAccessible(true);
        Constructor<Lhanshi> constructor = Lhanshi.class.getDeclaredConstructor(null);
        Lhanshi lhanshi2 = constructor.newInstance();
          constructor.setAccessible(true);
          flag.set(lhanshi2,false);
        Lhanshi lhanshi1 = constructor.newInstance();
        System.out.println(lhanshi2 == lhanshi1);

    }
}
