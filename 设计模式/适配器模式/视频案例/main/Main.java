package com.handFirst.适配器模式.视频案例.main;

import com.handFirst.适配器模式.视频案例.adapted.Adapted;
import com.handFirst.适配器模式.视频案例.adapter.impl.对象适配器.NetToUsb2;
import com.handFirst.适配器模式.视频案例.computer.Computer;

public class Main {
    public static void main(String[] args) {
        Computer computer = new Computer();
        NetToUsb2 netToUsb2 = new NetToUsb2();
        Adapted adapted = new Adapted();
        netToUsb2.setAdapted(adapted);
        computer.net(netToUsb2);

    }
}
