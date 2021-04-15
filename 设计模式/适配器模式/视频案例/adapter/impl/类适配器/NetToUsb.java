package com.handFirst.适配器模式.视频案例.adapter.impl.类适配器;

import com.handFirst.适配器模式.视频案例.adapted.Adapted;
import com.handFirst.适配器模式.视频案例.adapter.Adapter;

/**
 * 真正的Usb
 */
public class NetToUsb extends Adapted implements Adapter {
    /**
     * 处理亲求
     */
    @Override
    public void handRequest() {
       super.request();
    }
}
