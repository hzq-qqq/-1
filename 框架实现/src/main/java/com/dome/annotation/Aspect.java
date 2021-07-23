package com.dome.annotation;

import java.lang.annotation.*;

/**
 * 切面注解
 * @author hzq
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Aspect {
    /**
     * 值是一个注解类
     *  拦截所有的带Controller 注解的类 执行横切代码
     * @return
     */
    Class<? extends Annotation> value();
}
