package com.并发编程.nine;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntUnaryOperator;

@Slf4j
public class Main1 {
    public static void main(String[] args) {
        AtomicInteger i = new AtomicInteger(0);

//        System.out.println(i.incrementAndGet());
//        System.out.println(i.getAndIncrement());
//        System.out.println(i.getAndAdd(10));
//        System.out.println(i);


/**
 * 这里的x表示整形遍历能够读取到的value值
 *
 */
        System.out.println(i.updateAndGet(value -> value + 10));

        /**
         * 自己实现   updateAndGet
         */
       updateAndGet(i,value -> value / 2);
        System.out.println(i);
    }

    /**
     *
     * @param i
     * @param unaryOperator  一元 ——表示只接受一元的参数
     */
    public static void updateAndGet(AtomicInteger i, IntUnaryOperator unaryOperator){
//        这里不能将 next 的计算方式写死，而是应该将操作的方式传递进来
        while (true){
            int pre = i.get();
//            给了操作方式和旧的值，来计算新的值
            int next = unaryOperator.applyAsInt(pre);
            if (i.compareAndSet(pre,next)){
                break;
            }
        }
    }


}
