package com.dome.helper;


import com.dome.annotation.Controller;
import com.dome.annotation.Service;
import com.dome.util.ClassUtil;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

/**
 * 类操作助手类  —— 获取 包基础路径下的所有的 class， 选出标注了注解@Service @Controller 注解类，加入到容器中管理
 *
 *  —— 注意: 这里只是讲标有@Service @Controller 注解的类，加入到BeanClassSet 容器中，还没有真正的创建实例对象在容器中
 *      因此：需要提供一个反射类工具来创建所需要的Bean
 *
 * @author huangyong
 * @since 1.0.0
 */
public final class ClassHelper {

    /**
     * 定义类集合（用于存放所加载的类）
     *  基础包名下的所有类对象
     */
    private static final Set<Class<?>> CLASS_SET;

    static {
        String basePackage = ConfigHelper.getAppBasePackage();
//        getClassSet() 方法是整个应用程序的基础包名下的所有的类
        CLASS_SET = ClassUtil.getClassSet(basePackage);
    }

    /**
     * 获取应用包名下的所有类
     */
    public static Set<Class<?>> getClassSet() {
        return CLASS_SET;
    }

    /**
     * 获取应用包名下所有 Service 类
     * 使用hashSet 存储所有的service 注解修饰的类
     *
     *  遍历得到的所有的class 类，判断是否是=使用了@Service 注解修饰，如果是化，将当前类加入到set集合中管理
     */
    public static Set<Class<?>> getServiceClassSet() {
        Set<Class<?>> classSet = new HashSet<Class<?>>();
//        遍历用用程序下的所有的类，然后找出使用注解Service 修饰的类
        for (Class<?> cls : CLASS_SET) {
//            判断该类是否被Service 注解修饰
            if (cls.isAnnotationPresent(Service.class)) {
//               添加的集合中
                classSet.add(cls);
            }
        }
        return classSet;
    }

    /**
     * 获取应用包名下所有 Controller 类
     */
    public static Set<Class<?>> getControllerClassSet() {

        Set<Class<?>> classSet = new HashSet<Class<?>>();
        for (Class<?> cls : CLASS_SET) {
            if (cls.isAnnotationPresent(Controller.class)) {
                classSet.add(cls);
            }
        }
        return classSet;
    }

    /**
     * 获取应用包名下所有 Bean 类（包括：Service、Controller 等）
     *  即时获取所有使用了controller，service 注解修饰的类
     *    得到所有需要被管理的类
     */
    public static Set<Class<?>> getBeanClassSet() {
        Set<Class<?>> beanClassSet = new HashSet<Class<?>>();
//        获取标注有@Service @Controller 注解的类， 然后存入BeanClassSet 容器中管理
        beanClassSet.addAll(getServiceClassSet());
        beanClassSet.addAll(getControllerClassSet());
//        返回所有被 service controller 注解修饰的类
        return beanClassSet;
    }


//    以下先不看


    /**
     * 获取应用包名下某父类（或接口）的所有子类（或实现类）
     *   获得某父类接口下的所有的子类 或者实现类
     *
     *   得到某类的所有的子类    —— 这里是得到所有的切面抽象类的实现类
     *     抽象类理解  —— 抽象类提供了一个模板，不同的子类可以以不同的方式来实现
     */
    public static Set<Class<?>> getClassSetBySuper(Class<?> superClass) {
        Set<Class<?>> classSet = new HashSet<Class<?>>();
//        遍历整个应用基础包名下的所有的class类
        for (Class<?> cls : CLASS_SET) {
//            isAssignableFrom 判断两个类或者接口是否相同，或者 此类等价于 参数中的类的超类
//            第二个判断是用于 判断是 两个类是否相同
            if (superClass.isAssignableFrom(cls) && !superClass.equals(cls)) {
//                得到supperClass 的 所有的子类 cls
                classSet.add(cls);
            }
        }
        return classSet;
    }

    /**
     * 获取应用包名下带有某注解的所有类
     *
     * 得到注解  annotationClass 修饰的所有类
     */
    public static Set<Class<?>> getClassSetByAnnotation(Class<? extends Annotation> annotationClass) {
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        for (Class<?> cls : CLASS_SET) {
//            判断该类 是否使用了某注解
            if (cls.isAnnotationPresent(annotationClass)) {
                classSet.add(cls);
            }
        }
        return classSet;
    }
}
