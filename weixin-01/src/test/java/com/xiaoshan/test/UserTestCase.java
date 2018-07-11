package com.xiaoshan.test;

import com.xiaoshan.entity.User;
import com.xiaoshan.util.MyBatisUtils;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

/**
 * @author YushanZhao
 * @Date: 2018/7/9
 */
public class UserTestCase {

    Logger logger = LoggerFactory.getLogger(UserTestCase.class);

    @Test
    public void testSave() throws IOException {

        //1.读取mybatis主配置文件
       Reader reader = Resources.getResourceAsReader("mybatis.xml");
       //InputStream inputStream = Resources.getResourceAsStream("mybatis.xml");

        //创建sqlSessionFactory
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);

        //创建sqlSession
        SqlSession sqlSession = sqlSessionFactory.openSession(true);//加true自动提交事务

        //操作数据库
        User user = new User();
        user.setUsername("aaa");
        user.setPassword("111");

        int a = sqlSession.insert("com.xiaoshan.mapper.UserMapper.save",user);

        Assert.assertEquals(a , 1);

        //提交事务
        //sqlSession.commit();

        //释放资源
        sqlSession.close();

    }

    @Test
    public void testFindUserById(){


        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        User user = sqlSession.selectOne("com.xiaoshan.mapper.UserMapper.findUserById", 9);

        logger.debug("user:{}",user.toString());
        //System.out.println(user);
        sqlSession.close();

    }

    @Test
    public void testFindAll(){
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        List<User> userList = sqlSession.selectList("com.xiaoshan.mapper.UserMapper.findAll");

        for(User user : userList){
            logger.debug("user:{}",user.toString());
           // System.out.println(user);
        }
        sqlSession.close();

    }

    @Test
    public void testDeleteById(){
        SqlSession sqlSession = MyBatisUtils.getSqlSession(true);
        sqlSession.delete("com.xiaoshan.mapper.UserMapper.deleteById", 11);

        sqlSession.close();
    }

    @Test
    public void testUpdateById(){
        SqlSession sqlSession = MyBatisUtils.getSqlSession(true);
        User user = sqlSession.selectOne("com.xiaoshan.mapper.UserMapper.findUserById", "9");
        user.setUsername("huanhuan");
        user.setPassword("1313");

        sqlSession.update("com.xiaoshan.mapper.UserMapper.updateById", user);

        sqlSession.close();

    }


}


