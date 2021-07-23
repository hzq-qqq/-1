package com.并发编程.seven;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main4 {
    public static void main(String[] args) {
//        解决哲学家就餐问题——5个哲学家 5 5双筷子

//        如果获取了左手的筷子，然后请求右手的筷子，当在规定时间之内获取不到锁时，就释放自己持有的锁

//        主要是利用tryLock（）机制 当获取不到锁就返回false，然后继续向下执行，就会执行到释放自己持有的筷子
    }
}
