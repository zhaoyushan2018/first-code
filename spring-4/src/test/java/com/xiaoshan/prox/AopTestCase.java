package com.xiaoshan.prox;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author YushanZhao
 * @Date:2018/7/16
 */
public class AopTestCase {

    @Test
    public void testAop(){
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-aop.xml");
        Sale sale = (Sale) context.getBean("dell");
        sale.salePc();
        int in = sale.salePrice();
    }

}
