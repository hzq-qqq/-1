package com.handFirst.代理模式.动态代理模式.案例一;


/**
 * 1.基于接口的实现
 * 2.基于类的实现
 *
 *
 * 两个基本类
 *      1.invocationHandeler：调用处理程序
 *      2.
 */
public class Main {
    public static void main(String[] args) {
        Hoster hoster = new Hoster();
        Proxya proxya = new Proxya();
        proxya.setRent(hoster);
        Rent proxy = (Rent) proxya.getProxy();
        proxy.rent();
    }
}
