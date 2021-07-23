package com.并发编程.five.案例;

import com.并发编程.five.Main3;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Poster extends Thread{
    //        信箱的编号
    private Integer id;
    //        信息的内容
    private String mal;

    public Poster(Integer id, String mal) {
        this.id = id;
        this.mal = mal;
    }

    @SneakyThrows
    @Override
    public void run() {
//             获取该id 对应的信箱
        GetResult result =  Mainbox.getResult(id);
        log.debug("送信 id {} , 内容：{} ",id,mal);
        result.complate(mal);
    }
}
