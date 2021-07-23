package com.并发编程.five;

import com.并发编程.five.案例.Mainbox;
import com.并发编程.five.案例.People;
import com.并发编程.five.案例.Poster;
import lombok.extern.slf4j.Slf4j;


import static java.lang.Thread.sleep;

@Slf4j
public class Main3 {

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 3; i++) {
            new People().start();
        }
        sleep(3000);
        for (Integer id : Mainbox.getIds()) {
            new Poster(id, "内容" + id).start();
        }
    }
}
