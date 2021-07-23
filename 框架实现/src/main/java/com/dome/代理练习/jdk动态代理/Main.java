package com.dome.代理练习.jdk动态代理;


import com.dome.代理练习.静态代理.Hello;
import com.dome.代理练习.静态代理.HelloImpl;

import java.lang.reflect.Proxy;

public class Main {
    public static void main(String[] args) {
        Hello hello = new HelloImpl();

//        创建一个代理的hello 然后执行
        /**
         * 参数：
         *    被代理的类的类加载器
         *    被代理的类 实现的接口   ——  通过这里也可以发现 jdk 东动态的本质就是通过代理接口中实现的方法，
         *    所以代理的类一定要实现一个接口
         *    代理类
         */
//        这里是创建一个代理类
        Hello hello1 = new DynamicProxy(hello).getProxy();
        hello1.say("张三");

    }

}
