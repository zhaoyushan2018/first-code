package com.xiaoshan.service;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author YushanZhao
 * @Date:2018/7/14
 */
public class StudentServiceTestCase {

    @Test
    public void testSave(){

        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        StudentService studentService = (StudentService) context.getBean("studentService");
        studentService.save();
    }

}
