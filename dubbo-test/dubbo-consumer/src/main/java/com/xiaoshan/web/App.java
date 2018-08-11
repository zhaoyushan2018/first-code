package com.xiaoshan.web;

import com.xiaoshan.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * 调用远程服务
 *
 * @author YushanZhao
 * @Date:2018/8/11
 */
public class App {

    public static void main(String[] args) throws IOException {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring-dubbo-consumer.xml");
        UserService userService = (UserService) context.getBean("UserService");

        Integer id = 1;
        String name = userService.findById(id);
        System.out.println("同过Id:" + id + "获得名字为-->" + name);

        System.in.read();
    }

















}
