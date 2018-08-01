package com.xiaoshan.factory;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author YushanZhao
 * @Date:2018/7/31
 */
public class StudentTestCase {

    @Test
    public void testSayHello(){
        /*Student student = new Student();
        student.sayHello();*/

        StudentFactory studentFactory = new StudentFactory();
        studentFactory.getStudent().sayHello();
    }

    @Test
    public void testSpringStudentFactory(){

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        Student student = (Student) applicationContext.getBean("springStudentFactory");
        student.sayHello();
        System.out.println(student);
    }


}
