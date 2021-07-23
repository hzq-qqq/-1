package com.dome.helper;

import com.dome.util.ClassUtil;

/**
 * 用于加载ClassHelper ，BeanHelper，IOCHelper， ControllerHelper
 *   初始化框架
 *     _ 使用class.forName() 进行初始化
 */
public class HelperLoader {
    public static void init(){
        Class<?>[] classeList = {
                ClassHelper.class,
//                这里是将AOP 类加载到容器中 初始化
                AopHelper.class,
                BeanHelper.class,
                IocHelper.class,
                ControllerHelper.class
        };
        for (Class<?> aClass : classeList) {
            ClassUtil.loadClass(aClass.getName());
        }
    }
}
