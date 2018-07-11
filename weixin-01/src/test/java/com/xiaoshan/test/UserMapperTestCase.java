package com.xiaoshan.test;

import com.xiaoshan.entity.User;
import com.xiaoshan.mapper.UserMapper;
import com.xiaoshan.util.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jws.soap.SOAPBinding;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author YushanZhao
 * @Date:2018/7/10
 */
public class UserMapperTestCase {

    Logger logger = LoggerFactory.getLogger(UserMapperTestCase.class);

    private SqlSession sqlSession;
    private UserMapper userMapper;


    @Before
    public void before(){
        sqlSession = MyBatisUtils.getSqlSession();
        //动态代理，sqlSession对象根据接口的class动态创建接口的实现类
        userMapper = sqlSession.getMapper(UserMapper.class);
    }

    @After
    public void after(){
        sqlSession.close();
    }

    @Test
    public void testSave(){

        User user = new User();
        user.setUsername("zhenzhen");
        user.setPassword("123456");

        int count = userMapper.save(user);
        logger.debug("受影响的行数,{}", count);

        int id = user.getId();
        logger.debug("自动生成的主键值为:{}", id);

        sqlSession.commit();

    }

    @Test
    public void testFindUserById(){

        User user = userMapper.findUserById(13);
        logger.debug("user:{}",user.toString());

    }

    @Test
    public void testFindAll(){

        List<User> userList = userMapper.findAll();
        for(User user : userList){
            logger.debug("user:{}", user);
        }

    }

    @Test
    public void testDeleteById(){
        userMapper.deleteById(13);
        sqlSession.commit();
    }

    @Test
    public void testUpdate(){
        User user = userMapper.findUserById(9);
        user.setPassword("1314");

        userMapper.update(user);
        sqlSession.commit();
    }

    @Test
    public void testFindByPage(){
        List<User> userList = userMapper.findByPage(3,3);
        for (User user : userList){
            logger.debug("user:{}",user);
        }
    }

    @Test
    public void testFindPageByMap(){
        Map<String, Integer> maps = new HashMap<>();
        maps.put("start", 3);
        maps.put("pageSize", 4);
        List<User> userList = userMapper.findPageByMap(maps);
        for (User user : userList){
            logger.debug("user:{}", user);
        }
    }

}
