package com.dome.spring源码.循环依赖;

public class TestB {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private TestA a;

    public TestA getA() {
        return a;
    }

    public void setA(TestA a) {
        this.a = a;
    }

    @Override
    public String toString() {
        return "TestB{" +
                "name='" + name + '\'' +
                ", a=" + a +
                '}';
    }
}
