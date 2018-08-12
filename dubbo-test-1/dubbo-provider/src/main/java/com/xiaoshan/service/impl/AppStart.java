package com.xiaoshan.service.impl;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 *  启动容器
 * @author YushanZhao
 * @Date:2018/8/12
 */
public class AppStart {

    public static void main(String[] args) throws IOException {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring-dubbo-provider.xml");
        ((ClassPathXmlApplicationContext) context).start();
        System.out.println("---------->>>>>> 容器启动成功");
        //防止退出
        System.in.read();
    }

}
