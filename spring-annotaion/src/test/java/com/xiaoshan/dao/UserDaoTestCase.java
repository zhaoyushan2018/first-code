package com.xiaoshan.dao;

import com.xiaoshan.BaseTestCase;
import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
/**
 * @author YushanZhao
 * @Date:2018/7/16
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = Application.class)
public class UserDaoTestCase extends BaseTestCase {

    @Autowired
    private UserDao userDao;

    @Test
    public void testSave(){
        //ApplicationContext context = new AnnotationConfigApplicationContext(Application.class);
        //UserDao userDao = (UserDao) context.getBean("userDao");
        userDao.save();
    }















    public void testDataSource(){
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        basicDataSource.setUrl("jdbc:mysql:///test");
        basicDataSource.setUsername("root");
        basicDataSource.setPassword("root");

        System.out.println("----------------分割线----------------");

        basicDataSource.setInitialSize(5);
        basicDataSource.setMinIdle(4);
        basicDataSource.setMaxIdle(10);
        basicDataSource.setMaxWaitMillis(5000);//以毫秒为单位。一千毫秒等于一秒
    }

}


/*@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Application.class)
public class UserDaoTestCase {

    @Autowired
    private UserDao userDao;

    @Test
    public void testSave(){
        //ApplicationContext context = new AnnotationConfigApplicationContext(Application.class);
        //UserDao userDao = (UserDao) context.getBean("userDao");
        userDao.save();
    }
}*/


/*@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring.xml")
public class UserDaoTestCase {
    @Autowired
    private UserDao userDao;
    @Test
    public void testSave(){
        userDao.save();
    }
}*/


/*public class UserDaoTestCase {

    @Test
    public void testSave(){
        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        UserDao userDao = (UserDao) context.getBean("userDao");
        userDao.save();
    }

}*/
