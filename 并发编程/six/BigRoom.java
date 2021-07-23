package com.并发编程.six;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BigRoom {
        public void sleep() throws InterruptedException {
            synchronized (this) {
                log.debug("sleeping 2 小时");
               Thread.sleep(2);
            }
        }
        public void study() {
            synchronized (this) {
                log.debug("study 1 小时");
            }
        }
}
