package com.handFirst.代理模式.main;

import java.lang.reflect.Proxy;

public class Main {
    public static void main(String[] args) {
        Proxya proxya = new Proxya();
        proxya.setTarget(new Student());
        Student1 o = (Student1) Proxy.newProxyInstance(Proxya.class.getClassLoader(), Student.class.getInterfaces(), proxya);
       o.show();
    }
}
