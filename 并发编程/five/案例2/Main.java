package com.并发编程.five.案例2;

// 生产者消费模式——主色队列的底层实现原理
// 由于消息队列的信息被消费先进先消费的原则，所以该模式消息被消费有一定的延迟

public class Main {
    public static void main(String[] args) throws InterruptedException {
        MessagQueue mq = new MessagQueue(3);

        for (int i = 0; i < 3; i++) {
            final int value  = i;
            new Thread(()->{
//                接受客户端的请求并将其放入到消息队列中
              mq.put(new Message(value,"值为： " + value));
            },"生产者" + i).start();
        }

         Thread.sleep(3);
        for (int i = 0; i < 5; i++) {
            new Thread(mq::take,"消费者" + i).start();
        }
    }
}
