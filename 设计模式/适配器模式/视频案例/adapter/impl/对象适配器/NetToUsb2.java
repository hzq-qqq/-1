package com.handFirst.适配器模式.视频案例.adapter.impl.对象适配器;

import com.handFirst.适配器模式.视频案例.adapted.Adapted;
import com.handFirst.适配器模式.视频案例.adapter.Adapter;

/**
 * 真正的Usb
 */
public class NetToUsb2 implements Adapter {

    /**
     * 这里就是被适配的对象 网线
     */
    private Adapted adapted;

    public NetToUsb2(){

    }

    public void setAdapted(Adapted adapted) {
        this.adapted = adapted;
    }

    /**
     * 处理亲求
     */
    @Override
    public void handRequest() {
        adapted.request();
    }
}
