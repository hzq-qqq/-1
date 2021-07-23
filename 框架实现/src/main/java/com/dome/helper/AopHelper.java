package com.dome.helper;

import com.dome.annotation.Aspect;
import com.dome.annotation.Service;
import com.dome.aspect.TransactionProxy;
import com.dome.proxy.AspectProxy;
import com.dome.proxy.Proxy;
import com.dome.proxy.ProxyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.lang.annotation.Annotation;
import java.util.*;


public final class AopHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(AopHelper.class);

    /**
     * 初始化整个AOP框架    —— 间被代理类 到代理执行链之间的映射
     */
    static {
        try {
//        创建切面类到  被代理类之间的映射
            Map<Class<?>, Set<Class<?>>> proxyMap = createProxyMap();
//        创建被代理类对象到代理对象之间的映射
            Map<Class<?>, List<Proxy>> targetMap = createTargetMap(proxyMap);
            for (Map.Entry<Class<?>, List<Proxy>> targetEntry : targetMap.entrySet()) {
                Class<?> targetClass = targetEntry.getKey();
                List<Proxy> proxyList = targetEntry.getValue();
//                创建代理执行链
                Object proxy = ProxyManager.createProxy(targetClass, proxyList);
//                创建被代理类到 代理执行对象之间的映射 存入MAP 中  —— 代理执行就是 真正执行代理的类对象
                BeanHelper.setBean(targetClass,proxy);
            }
        } catch (Exception e) {
            LOGGER.error("aop failure ", e);
        }
    }

    /**
     * 得到所有代理类 和  被代理类的映射Map
     * @return
     */
    private static Map<Class<?>,Set<Class<?>>>createProxyMap(){
        Map<Class<?>,Set<Class<?>>> proxyMap = new HashMap<Class<?>, Set<Class<?>>>();
//        添加普通的代理类 到 被代理类之间的映射
         addAspectProxy(proxyMap);
//        添加事务控制控制类 到被代理类之间的映射map
         addTransactionProxy(proxyMap);
         return proxyMap;
    }

    /**
     * 添加事务控制类 到 service 类的映射
     * 在service 类执行方法的时候，会去判断该方法是否发使用了Transaction 注解来判断是否需要开启事务
     * @param proxyMap
     */
    private static void addTransactionProxy(Map<Class<?>, Set<Class<?>>> proxyMap) {
        Set<Class<?>> serviceClassSet = ClassHelper.getClassSetByAnnotation(Service.class);
        proxyMap.put(TransactionProxy.class,serviceClassSet);
    }

    /**
     * 得到切面类的实现类 到 被代理类之间的映射Map
     * @param proxyMap
     */
    private static void addAspectProxy(Map<Class<?>, Set<Class<?>>> proxyMap) {
//        AspectProxy 注解的的所有的子类
        Set<Class<?>> proxyClassSet = ClassHelper.getClassSetBySuper(AspectProxy.class);
        for (Class<?> proxyClass : proxyClassSet) {
            Aspect aspect = proxyClass.getAnnotation(Aspect.class);
//            得到代理类的实现类 到 被代理类之间的映射
            Set<Class<?>> targetClassSet = createTargetClassSet(aspect);
            proxyMap.put(proxyClass,targetClassSet);
        }
    }

    /**
     *  获取 需要被代代理的所有类
     *     就是在Aspect 注解里面配置的注解 ， 然后带有该注解的类就是需要被代理的
     * @param aspect
     * @return
     * @throws Exception
     */
    private static Set<Class<?>>createTargetClassSet(Aspect aspect) {
        HashSet<Class<?>> targetClassSet = null;
        try {
            targetClassSet = new HashSet<Class<?>>();
            Class<? extends Annotation> annotation = aspect.value();
//        注解类不能是 使用了Aspect 注解的类
            if (annotation != null && !annotation.equals(Aspect.class)){
                targetClassSet.addAll(ClassHelper.getClassSetByAnnotation(annotation));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return targetClassSet;
    }

    /**
     * 创建目标类到 代理对象之间的映射Map
     * @param proxyMap 代理类和被代理类之间的映射
     * @return
     */
    private static Map<Class<?>, List<Proxy>> createTargetMap(Map<Class<?>,Set<Class<?>>> proxyMap)throws Exception{
        Map<Class<?>,List<Proxy>>targetMap = new HashMap<Class<?>, List<Proxy>>();
        for (Map.Entry<Class<?>, Set<Class<?>>> proxyEntry : proxyMap.entrySet()) {
//            得到切面类 —— 切面类
            Class<?> proxyClass = proxyEntry.getKey();
//            得到目标类集合   —— 遍历代理类 —— 创建代理类对象 得到代理对象的集合
            Set<Class<?>> targetClasss = proxyEntry.getValue();
            for (Class<?> targetClass : targetClasss) {
//               给每个被代理类都映射 所有的切面类类对象
                Proxy proxy = (Proxy) proxyClass.newInstance();
                if (targetMap.containsKey(targetClass)){
//                    map 中存在该目标类到 代理类之间的映射 ，就将整个新的代理类加入到原来代理类的list的末尾（哈希冲突）
                    targetMap.get(targetClass).add(proxy);
                }else {
//                    映射中不存在 该目标类的映射，就创建一个新的 list 存储映射
                    ArrayList<Proxy> proxyArrayList = new ArrayList<Proxy>();
                    proxyArrayList.add(proxy);
                    targetMap.put(targetClass,proxyArrayList);
                }
            }
        }
        return targetMap;

    }



}
