package com.xiaoshan.service;

import com.xiaoshan.Application;
import com.xiaoshan.BaseTestCase;
import com.xiaoshan.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author YushanZhao
 * @Date:2018/7/16
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = Application.class)
public class UserServiceTestCase extends BaseTestCase {

    @Autowired
    private UserService userService;

    @Test
    public void testSave(){
        //ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        //UserService userService = (UserService) context.getBean("userService");
        userService.save();
    }

}



/*@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring.xml")
public class UserServiceTestCase {

    @Autowired
    private UserService userService;

    @Test
    public void testSave(){
        //ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        //UserService userService = (UserService) context.getBean("userService");
        userService.save();
    }

}*/
