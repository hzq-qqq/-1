package com.dome.代理练习.基于注解的拦截;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * 拦截使用了某个注解的方法
 */
@Aspect
public class GreetAspect {

    /**
     * 拦截该注解标注下的所有的方法
     * @param pjp
     * @return
     */
    @Around("@annotation(com.dome.代理练习.基于注解的拦截.Tag)")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        before();
        Object result = pjp.proceed();
        after();
        return result;
    }

    private void after() {
        System.out.println("after");
    }

    private void before() {
        System.out.println("before");
    }
}
