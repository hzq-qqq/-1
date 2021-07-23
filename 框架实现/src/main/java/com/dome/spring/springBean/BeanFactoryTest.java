package com.dome.spring.springBean;

import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

/**
 * @author hzq
 */
public class BeanFactoryTest {

    @Test
    public void test(){
        BeanFactory bf = new XmlBeanFactory(new ClassPathResource("BeanFactory.xml"));
       MyTestBean myTestBean = (MyTestBean) bf.getBean("MyTestBean");

        System.out.println(myTestBean.toString());
    }
}
