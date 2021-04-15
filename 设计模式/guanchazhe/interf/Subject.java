package com.handFirst.guanchazhe.interf;

public interface Subject {

    public void register(Observe obj);

    public void release(Observe obj);

    public void notifyObserver();
}
