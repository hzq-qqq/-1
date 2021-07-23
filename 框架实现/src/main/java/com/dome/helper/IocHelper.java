package com.dome.helper;

import com.dome.annotation.Inject;
import com.dome.util.ArrayUtil;
import com.dome.util.CollectionUtil;
import com.dome.util.ReflectionUtil;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

/**
 * 依赖注入
 *   实现思路：
 *        遍历整个BEAN_MAP 获得所有的Class，和Object ，然后通过反射获取该class 的field 遍历field ，
 *        判断属性是否标注了 @Inject 注解，如果标注了，就从Bean_map 中取出 该bean， 最后调用Reflection.setField, 注入值
 */
public final class IocHelper {

//    在一个同一的地方加载这个IocHelper 来实现依赖注入  —— 可以是在运行时生效， 可以是在编译时生效

    /**
     * 这里的IOC 容器中的所有的对象都是单例的，应为这里的Object 依然是BeanClassSet 中获取的，并不是额外新创建的（多列）
     */

    static {
        final Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();
        if (CollectionUtil.isNotEmpty(beanMap)){
            for (Map.Entry<Class<?>, Object> classObjectEntry : beanMap.entrySet()) {
                final Class<?> aClass = classObjectEntry.getKey();
                final Object object = classObjectEntry.getValue();

                final Field[] fields = aClass.getDeclaredFields();
                if (ArrayUtil.isNotEmpty(fields)){
//                    遍历field 的 所有属性
                    for (Field field : fields) {
//                        是否标有注入注解
                        if (field.isAnnotationPresent(Inject.class)){
//                            获取成员变量的类型 —— 把比如 Service 类型 Dao 类型 等等
                            final Class<?> classType = field.getType();
                            final Object temObj = beanMap.get(classType);
//                          从容器中获取 该class 对应的对象
                            if (temObj != null){
                                ReflectionUtil.setField(object,field,temObj);
                            }
                        }
                    }
                }
            }
        }
    }

}
