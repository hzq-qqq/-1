package com.handFirst.zhuangshizhe.复习.main;

import com.handFirst.zhuangshizhe.复习.jk.Jk;
import com.handFirst.zhuangshizhe.复习.jl.Jl;
import com.handFirst.zhuangshizhe.复习.zsl.impl.Zsl1;
import com.handFirst.zhuangshizhe.复习.zsl.impl.Zsl2;
import com.handFirst.zhuangshizhe.复习.zsl.impl.Zsl3;

public class Main {
    public static void main(String[] args) {
        Jk jl = new Jl();
        jl = new Zsl1(jl);
        jl = new Zsl2(jl);
        jl = new Zsl3(jl);

        jl.cost();
    }
}
