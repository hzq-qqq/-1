package com.handFirst.封装算法.student;

public class Student {
    private Integer age;

    public Integer getAge() {
        return age;
    }

    public Student() {
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Student{" +
                "age=" + age +
                '}';
    }
}
