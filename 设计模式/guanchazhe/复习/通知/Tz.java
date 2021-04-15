package com.handFirst.guanchazhe.复习.通知;

import com.handFirst.guanchazhe.复习.被通知.Btz;

public interface Tz {
    void register(Btz btz);
    void remove(Btz btz);
    void notifyall();
}
