package com.xiaoshan.dao.com.xiaoshan.service;

import com.xiaoshan.service.UserService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author YushanZhao
 * @Date:2018/7/16
 */
public class UserServiceTestCase {

    @Test
    public void testSave(){
        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        UserService userService = (UserService) context.getBean("userService");
        userService.save();
    }

}
