package com.dome.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 反射Util  —— 反射创建对象， 给对对象设置值， 调用方法
 */
public final class ReflectionUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReflectionUtil.class);


    /**
     * 创建实例对象
     * @param cls
     * @return
     */
    public static Object newInstance(Class<?> cls){
        Object instance;
        try {
            instance =  cls.newInstance();
        } catch (Exception e) {
            LOGGER.error("new Instance failed....",e);
            throw  new RuntimeException();
        }
        return instance;
    }

    /**
     * 调用方法
     */
    public static Object invokeMethod(Object obj, Method method, Object... args) {
        Object result;
        try {
            method.setAccessible(true);
            result = method.invoke(obj, args);
        } catch (Exception e) {
            LOGGER.error("invoke method failure", e);
            throw new RuntimeException(e);
        }
        return result;
    }


    /**
     * 设置某个成员变量的值
     * @param obj      某个对象
     * @param field    对象的field 对象
     * @param value    要设置的值
     */
    public static void setField(Object obj, Field field , Object value){
        field.setAccessible(true);
        try {
            field.set(obj,value);
        } catch (IllegalAccessException e) {
            LOGGER.error("setField failed...",e);
            throw new RuntimeException();
        }
    }




}
