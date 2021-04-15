package com.handFirst.代理模式.动态代理模式.一个万能的动态代理实现;

public class Main {
    public static void main(String[] args) {
        Student student = new Student();

        Proxywn proxywn = new Proxywn();

        proxywn.setTarget(student);
        StudentInter proixy = (StudentInter ) proxywn.getProixy();
        proixy.show();
    }
}
