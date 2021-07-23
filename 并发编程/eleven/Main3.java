package com.并发编程.eleven;

public class Main3 {
    static int value = 10;

    public static void main(String[] args) {
        /**
         * 读取final 的优化   将小的数字直接复制大方法栈中 ， 较 大的数字则放在常量池中  ——
         * 两者在获取值的时候的效率都比getstatic —— 从共享变量中获取数据的效率高
         *
         */
    }

}
