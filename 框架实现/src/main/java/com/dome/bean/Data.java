package com.dome.bean;

import java.util.Map;

/**
 * 返回的数据类型为json 类型
 */
public class Data {
    private final Object object;

    public Data(Object object){
        this.object = object;
    }

    public Object getModel(){
        return object;
    }
}
