package com.handFirst.代理模式.main;

public class Student implements Student1{
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    @Override
    public void show() {
        System.out.println("show");
    }
}
