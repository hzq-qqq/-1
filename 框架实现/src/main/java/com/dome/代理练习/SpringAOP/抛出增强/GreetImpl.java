package com.dome.代理练习.SpringAOP.抛出增强;

import com.dome.代理练习.SpringAOP.spring三个通知.Greet;

/**
 * @author hzq
 */
public class GreetImpl implements Greet {
    @Override
    public void say() {
        System.out.println("hello: " + "kangakng");

        throw new RuntimeException("error");
    }
}
