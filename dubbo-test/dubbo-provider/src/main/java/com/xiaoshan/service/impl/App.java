package com.xiaoshan.service.impl;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * 启动Spring容器
 * (用main方法或者test里面)
 * @author YushanZhao
 * @Date:2018/8/11
 */
public class App {

    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring-dubbo-provider.xml");
        context.start();

        System.out.println("-----------------> > > > > > > > > > > > > > >容器启动成功");

        //防止退出
        System.in.read();
    }














}
