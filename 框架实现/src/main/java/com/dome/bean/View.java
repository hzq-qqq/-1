package com.dome.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * 用于返回视图   —— 类似于一个 springBoot 中的 modelAndView
 *     需要类数据：
 *         1.返回的路径
 *         2.返回携带的参数 —— 比如 返回用户信息 username 等
 */
public class View {
    /**
     * 视图路径  —— 返回需要的视图的路径
     */
    private final String path;

    /**
     * 模型数据
     */
    private final Map<String,Object>model;

    public View(String path){
        this.path = path;
        model = new HashMap<String, Object>();
    }

    public View addModel(String key,Object value){
        model.put(key,value);
        return this;
    }
    public String getPath(){
        return path;
    }

    public Map<String,Object> getModel(){
        return model;
    }

}
