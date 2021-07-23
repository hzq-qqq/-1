package com.并发编程.ten;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

@Slf4j
public class Main {
    public static void main(String[] args) {
//        Student student = new Student();
//
//        /**
//         * 原子更新器
//         *  原子更新对象的属性  ——  属性不能是 privated 修饰的
//         *     注意属性 被 volatile 修饰 —— 因为cas 必须结合volatile 来保证共享变量的可见性
//         * Student  类
//         * String 属性的类型
//         * name  属性的名称
//         */
//        AtomicReferenceFieldUpdater<Student, String> updater = AtomicReferenceFieldUpdater.newUpdater(Student.class, String.class, "name");
//        updater.compareAndSet(student,null,"张三");
//
//        System.out.println(student);



    }

}
