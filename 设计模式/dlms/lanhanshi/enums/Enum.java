package com.handFirst.dlms.lanhanshi.enums;

import java.lang.reflect.Constructor;

public enum Enum {
    Instance;

    public static Enum getInstance() {
        return Instance;
    }
}
    class test {
        public static void main(String[] args) throws Exception {
            Enum instance = Enum.getInstance();
            Constructor<Enum> constructor = Enum.class.getDeclaredConstructor(String.class,int.class);
            constructor.setAccessible(true);
            Enum anEnum = constructor.newInstance();

            System.out.println(instance == anEnum);
        }
    }

