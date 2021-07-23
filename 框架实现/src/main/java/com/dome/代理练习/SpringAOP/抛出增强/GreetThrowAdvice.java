package com.dome.代理练习.SpringAOP.抛出增强;


import org.springframework.aop.ThrowsAdvice;


public class GreetThrowAdvice implements ThrowsAdvice {

    public void afterThrowing(){
        System.out.println("Exception 处理");
    }
}
