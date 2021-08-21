package com.dome.spring源码.循环依赖;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Mian {
    public static void main(String[] args) {
        ApplicationContext bf = new ClassPathXmlApplicationContext("beanTest.xml");
        TestA a = bf.getBean("a", TestA.class);
        TestB b = bf.getBean("b", TestB.class);

    }
}
