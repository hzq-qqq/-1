package com.handFirst.适配器模式.书本上的anli.Trukey;

public class FirstTurkey implements Trukey{
    @Override
    public void quack() {
        System.out.println("FirstTurkey quack");
    }

    @Override
    public void fly() {
        System.out.println("FirstTurkey fly");
    }
}
