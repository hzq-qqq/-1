package com.IO.BIO.复习案例.util;

import java.nio.ByteBuffer;

public class Debug {
    public static void debugall(ByteBuffer buffer) {
        System.out.println("postion : " + buffer.position());

        System.out.println("limit : " + buffer.limit());

    }
}
