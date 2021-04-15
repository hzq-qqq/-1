package com.handFirst.适配器模式.视频案例.computer;

import com.handFirst.适配器模式.视频案例.adapter.Adapter;

public class Computer {

    /**
     * 接口用于处理转接请求
     * @param adapter
     */
    public void net(Adapter adapter){
        adapter.handRequest();
    }

}
