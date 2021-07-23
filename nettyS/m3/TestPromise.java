package com.nettyS.m3;

import io.netty.channel.EventLoop;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.DefaultPromise;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;

@Slf4j
public class TestPromise {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        自己定义future 的创建权和结果的控制权

        final EventLoop loop = new NioEventLoopGroup().next();
//        2.创建 promise 对象  promis 就是结果的容器
        DefaultPromise<Integer> promise = new DefaultPromise<>(loop);
        new Thread(()->{
//            3. 任意一个线程执行计算，想promise 对象填结果
           log.debug("开始计算");
            try {
                Thread.sleep(1000);
                promise.setSuccess(80);
            } catch (Exception e) {
                e.printStackTrace();
                promise.setFailure(e);
            }
        }).start();

//        4.设置一个接受结果的线程
        log.debug("等待结果");
        log.debug("计算出错了{}",promise.get());
        log.debug("结果是：{}",promise.get());
    }
}
