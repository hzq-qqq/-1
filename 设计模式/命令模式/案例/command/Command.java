package com.handFirst.命令模式.案例.command;

public interface Command {
    /**
     * 执行命令
     */
    void excute();

    /**
     * 撤销命令
     */
    void updo();


}
