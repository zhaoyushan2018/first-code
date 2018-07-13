package com.xiaoshan.test;

import com.xiaoshan.dao.MovieDao;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author YushanZhao
 * @Date:2018/7/13
 */
public class MovieDaoSpringTestCase {

    @Test
    public void testSave(){

        /*ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        MovieDao movieDao = (MovieDao) applicationContext.getBean("movieDao");
        movieDao.save();
        MovieDao movieDao2 = (MovieDao) applicationContext.getBean("movieDao");
        movieDao2.save();
        System.out.println(movieDao == movieDao2);*/


        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        MovieDao movieDao = (MovieDao) context.getBean("movieDao");

        //让容器关闭
        context.close();

    }

























}
