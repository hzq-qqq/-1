package com.dome.aspect;

import com.dome.annotation.Transaction;
import com.dome.helper.DatabaseHelper;
import com.dome.proxy.Proxy;
import com.dome.proxy.ProxyChain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * 事务切面类
 * @author hzq
 */
public class TransactionProxy implements Proxy {
    private static final Logger logger = LoggerFactory.getLogger(ControllerAspect.class);
//     盲猜测 ： 表示当前方法所处于的状态  false：为开启事务  true: 已开启事务
//     用于保证 不同线程下，事务的执行逻辑只会被执行一次 —— 比如：不会重复的开启事务
    private static final ThreadLocal<Boolean> FLAG_HOLDER = new ThreadLocal<Boolean>(){
        @Override
        protected Boolean initialValue() {
            return false;
        }
    };

    @Override
    public Object doProxy(ProxyChain proxyChain) throws Throwable {
        Object result;
        Boolean flag = FLAG_HOLDER.get();
        Method meghod = proxyChain.getTargetMethod();
//        开启事务执行
        if (!flag && meghod.isAnnotationPresent(Transaction.class)){
//            设置当前方法为开启了事务
            FLAG_HOLDER.set(true);
            try {
                DatabaseHelper.beginTransaction();
                logger.debug("begin transaction");
                 result = proxyChain.doProxyChain();
                 DatabaseHelper.commitTransaction();
            } catch (Exception e) {

                logger.error("rollback transaction");
                DatabaseHelper.rollbackTransaction();
                throw  e;
            }finally {
                FLAG_HOLDER.remove();
            }
        }else {
//            这里是未开启事务 执行
            result  = proxyChain.doProxyChain();
        }
        return result;
    }
}
