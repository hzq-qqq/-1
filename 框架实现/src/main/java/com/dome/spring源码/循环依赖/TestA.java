package com.dome.spring源码.循环依赖;

public class TestA {
    private String name;
    private TestB b;

    public TestB getB() {
        return b;
    }

    public void setB(TestB b) {
        this.b = b;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "TestA{" +
                "name='" + name + '\'' +
                ", b=" + b +
                '}';
    }
}
