package com.dome.aspect;

import com.dome.annotation.Aspect;
import com.dome.annotation.Controller;
import com.dome.proxy.AspectProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * 拦截Controller 注解下的所有的方法
 */
@Aspect(Controller.class)
public class ControllerAspect extends AspectProxy {
    private static final Logger logger = LoggerFactory.getLogger(ControllerAspect.class);

    private Long begin;

    @Override
    public void before(Class<?> cls, Method method, Object[] params){
        logger.debug("-------------begin-------------");
        logger.debug(String.format("class: %s",cls.getName()));
        logger.debug(String.format("method: %s",method.getName()));
        begin = System.currentTimeMillis();
    }

    @Override
    public void after(Class<?> cls, Method method, Object[] params, Object result) {
        logger.debug(String.format("time: %dms",(System.currentTimeMillis()) - begin));
        logger.debug("-------------after-------------");
    }
}
