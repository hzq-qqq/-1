package com.dome.代理练习.aspectJ;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * Spring + AspectJ的方式配置切面
 *   切面：  增强 +  切入点（就是要不被增强的方法）
 *     通过切入点表达式来匹配要被增强的方法
 *
 *
 *     一下是切面   ——  每个方法通过配置切入点表达是来匹配要被增强的方法
 *
 *
 *
 */
@Aspect
public class GreetAspect {


    /**
     * 环绕通知
     * @param pjp   连接点 —— 可以连接到任何被拦截的方法的任何信息，方法的名字 参数等 ，然后可以执行
     * @return
     * @throws Throwable
     */
//    这个是配置切入点表达式 ： 用于匹配要被执行的方法
//    这个震正则表达式的含义是 ： 拦截所有aspectJ 包下任意返回值的方法 参数也可以是任意值
    @Around("execution(* com.dome.代理练习.aspectJ. *(..))")
    public Object around(ProceedingJoinPoint pjp) throws Throwable{
        before();
        Object result = pjp.proceed();
        after();
        return  result;
    }

    private void after() {
        System.out.println("after");
    }

    private void before() {
        System.out.println("before");
    }



}
