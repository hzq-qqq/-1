package com.并发编程.five.案例;

public class GetResult {
    //        居民和poster 使用同一个id号 在同一个id号对应的箱子里面进行书的存储和获取
    private Integer id;
    private Object result;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public GetResult(Integer id) {
        this.id = id;
    }

    public Object get(long timeOut) throws InterruptedException {
        synchronized (this) {
            long passTime = 0;
            long str = System.currentTimeMillis();
            while (result == null) {
                long waitTime = timeOut - passTime;
                if (waitTime <= 0) {
                    break;
                }
                this.wait(waitTime);
                passTime = System.currentTimeMillis() - str;
            }
        }
        return result;
    }

    public void complate(Object obj) throws InterruptedException {
        synchronized (this) {
            this.result = obj;
            this.notifyAll();
        }
    }
}
