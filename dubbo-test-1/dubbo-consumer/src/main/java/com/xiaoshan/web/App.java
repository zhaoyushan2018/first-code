package com.xiaoshan.web;

import com.xiaoshan.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * @author YushanZhao
 * @Date:2018/8/12
 */
public class App {

    public static void main(String[] args) throws IOException {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring-dubbo-consumer.xml");
        UserService userService = (UserService) context.getBean("UserService");

        Integer userId = 022;
        String userName = userService.findUserNameById(userId);
        System.out.println("根据userId为:" + userId + "------>>>>>> 查出来的userName为: " + userName);

        //防止退出
        System.in.read();
    }













}
