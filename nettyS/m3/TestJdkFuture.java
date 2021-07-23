package com.nettyS.m3;

import lombok.extern.slf4j.Slf4j;
import org.omg.PortableServer.POA;

import java.util.concurrent.*;

@Slf4j
public class TestJdkFuture {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        final ExecutorService pool = Executors.newFixedThreadPool(2);
        Future<Integer> future = pool.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                log.debug("计算执行");
                Thread.sleep(1000);
                return 50;
            }
        });
        log.debug("等待结果");
        System.out.println("结果是 ：" + future.get());
    }
}
