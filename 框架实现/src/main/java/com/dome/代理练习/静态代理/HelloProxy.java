package com.dome.代理练习.静态代理;

/**
 * 用一个HelloProxy 类来调用hello 方法，在方法之做一些事
 */
public class HelloProxy implements Hello{
    private Hello hello;

    public HelloProxy() {

    }


    @Override
    public void say(String name) {
        before();
        hello.say(name);
        after();
    }

    private void after() {
        System.out.println("after");
    }

    private void before() {
        System.out.println("before");
    }
}
