package com.dome.helper;

import com.dome.util.ReflectionUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *  根据BeanClassSet 管理的class ，反射创建 对象，存储在 map<class><Object> 中</></>
 */
public final class BeanHelper {
//    定义Bean 映射， 用于映射aclass ——> 实例对象
    private static final Map<Class<?>,Object> BEAN_MAP = new HashMap<Class<?>, Object>();

    static {
        final Set<Class<?>> beanClassSet = ClassHelper.getBeanClassSet();
        for (Class<?> aClass : beanClassSet) {
//            调用反射工具类创建Instance 对象
            final Object obj = ReflectionUtil.newInstance(aClass);
            BEAN_MAP.put(aClass,obj);
        }
    }

    /**
     * 获的Bean 映射
     * @return
     */
    public static Map<Class<?>,Object> getBeanMap(){
        return BEAN_MAP;
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(Class<T> cls){
        if (!BEAN_MAP.containsKey(cls)){
            throw new RuntimeException("Can not find class: " + cls);
        }
        return (T)BEAN_MAP.get(cls);
    }

    /**
     * 设置Bean 实例
     * @param cls
     * @param obj
     */
    public static void setBean(Class<?>cls, Object obj){
        BEAN_MAP.put(cls,obj);
    }




}
