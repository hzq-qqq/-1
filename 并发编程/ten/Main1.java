package com.并发编程.ten;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Slf4j
public class Main1 {
    public static void main(String[] args) {

       demo(()->new AtomicInteger(0),(adder) -> adder.getAndIncrement());

        /**
         * 原子累加器 ——  juc 包提供的 比原来的AtomicInteger 在性能上有质的提升
         *
         * 优化原理的理解：
         *      这里是在共享变量上进行累加操纵，前者采用的则是常规的cas 操作，在累加发生竞争时，采取的是重复循环尝试进行累加
         *
         *      后者采用的也是基本的cas ，但是不同的是，在发生竞争的时候，不在是重复的在一个 变量单元上进行累加操作，而是
         *      将共享变量一分为多个累加单元，不同的线程在不同的累加单元上操作，最终将结果汇总—— 简单来说就是设置了多个累加
         *      单元，减少了线程累加的失败次数，进而提高性能
         */
      demo(() -> new LongAdder(),adder -> adder.increment());

    }
    private static <T> void demo(Supplier<T> adderSupplier, Consumer<T> action) {
        T adder = adderSupplier.get();
        long start = System.nanoTime();
        List<Thread> ts = new ArrayList<>();
        // 4 个线程，每人累加 50 万
        for (int i = 0; i < 40; i++) {
            ts.add(new Thread(() -> {
                for (int j = 0; j < 500000; j++) {
                    action.accept(adder);
                }
            }));
        }
        ts.forEach(t -> t.start());
        ts.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    long end = System.nanoTime();
 System.out.println(adder + " cost:" + (end - start)/1000_000);
}

}

