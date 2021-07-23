package com.并发编程.five.案例;


import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class People extends Thread{

    @SneakyThrows
    @Override
    public void run() {
        GetResult pg = Mainbox.createGet();
        log.debug("开始收信 id {} ", pg.getId());
        Object mal = pg.get(3000);
        log.debug("收到的信息是 id {} , 内容 {} ", pg.getId(), mal);
        Mainbox.remove(pg.getId());
    }
}
