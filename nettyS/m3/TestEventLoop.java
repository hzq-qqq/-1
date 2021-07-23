package com.nettyS.m3;

import io.netty.channel.DefaultEventLoopGroup;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.NettyRuntime;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class TestEventLoop {
    public static void main(String[] args) {
        EventLoopGroup group = new NioEventLoopGroup(2);  // io  普通任务  定时任务
//        EventLoopGroup dfelgp = new DefaultEventLoopGroup();   // 处理普通任务 和定时任务

//        获取下一个事件循环对象
//        System.out.println(group.next());
//        System.out.println(group.next());
//        System.out.println(group.next());
//        System.out.println(group.next());
//        System.out.println(group.next());

//        普通任务
//        group.next().submit(()->{
//            log.debug("ok");
//        });
//        定时执行

        group.next().scheduleAtFixedRate(()->{
            log.debug("ok");
        }, 1,1, TimeUnit.SECONDS);

        log.debug("main");

    }
}
