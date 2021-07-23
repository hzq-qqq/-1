package com.nettyS.m3;

import io.netty.channel.EventLoop;
import io.netty.channel.nio.NioEventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

@Slf4j
public class TestNettyFuture {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        NioEventLoopGroup group = new NioEventLoopGroup();

        final EventLoop eventLoop = group.next();
        final Future<Integer> future = eventLoop.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                log.debug("计算执行");
                Thread.sleep(1000);
                return 100;
            }
        });

        log.debug("等带结果");
//        同步方式获取结果
//        log.debug("结果是：{} ",future.get());
//        异步方式获取结果
         future.addListener(new GenericFutureListener<Future<? super Integer>>() {
             @Override
//             这个方法是在 nio线程中执行
             public void operationComplete(Future<? super Integer> future) throws Exception {
                 log.debug("接受结果 ：{} ",future.getNow());
             }
         });
    }
}
