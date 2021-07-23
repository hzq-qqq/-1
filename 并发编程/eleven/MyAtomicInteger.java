//package com.并发编程.eleven;
//
//
//import sun.misc.Unsafe;
//
//import java.lang.reflect.Field;
//import java.util.ArrayList;
//
///**
// * 子定义原子整数类
// * Unsafe 底层是去调用cpu的cas操作
// */
//public class MyAtomicInteger {
//    private volatile int value = 10000;
//    private static Unsafe UNSAFE;
//    private static long valueOffset;
//    static {
//        try {
//            Field field = Unsafe.class.getDeclaredField("theUnsafe");
//            UNSAFE = (Unsafe) field.get(null);
//            valueOffset = UNSAFE.objectFieldOffset(MyAtomicInteger.class.getDeclaredField("value"));
//        } catch (NoSuchFieldException | IllegalAccessException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void decrement(int amount) {
//        while (true) {
//            int pre = this.value;
//            int next = this.value - amount;
//            if (UNSAFE.compareAndSwapInt(this, valueOffset, pre, next)){
//                break;
//            }
//        }
//    }
//    static MyAtomicInteger integer = new MyAtomicInteger();
//    public static void main(String[] args) {
//
//
//    }
//    static void test2(){
//        long str = System.currentTimeMillis();
//        ArrayList<Thread> list = new ArrayList<>();
//        for (int i = 0; i < 5; i++) {
//            list.add( new Thread(()->{
//                for (int i1 = 0; i1 < 2000; i1++) {
//                   integer.decrement(1);
//                }
//            }));
//        }
//        list.forEach(t -> t.start());
//
//        list.forEach(t -> {
//            try {
//                t.join();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        });
//        long end = System.currentTimeMillis();
//        System.out.println("time : " + (end - str));
//        System.out.println("result:  " + integer.get());
//    }
//
//    private int get() {
//        return this.value;
//    }
//}
