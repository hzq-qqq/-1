package com.handFirst.代理模式.动态代理模式.一个万能的动态代理实现;

public class Student implements StudentInter {

    @Override
    public void show(){
        System.out.println("show方法执行了");
    }
    public void delete(){
        System.out.println("delete");
    }
    public void add(){
        System.out.println("add");
    }
}
