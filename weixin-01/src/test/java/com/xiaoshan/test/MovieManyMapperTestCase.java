package com.xiaoshan.test;

import com.xiaoshan.entity.Movie;
import com.xiaoshan.entity.Type;
import com.xiaoshan.mapper.MovieManyMapper;
import com.xiaoshan.util.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * @author YushanZhao
 * @Date:2018/7/10
 */
public class MovieManyMapperTestCase {

    Logger logger = LoggerFactory.getLogger(MovieManyMapperTestCase.class);

    private SqlSession sqlSession;
    private MovieManyMapper movieManyMapper;

    @Before
    public void before(){
        sqlSession = MyBatisUtils.getSqlSession();
        // 动态代理：sqlSession对象根据接口的class动态创建接口的实现类
        movieManyMapper = sqlSession.getMapper(MovieManyMapper.class);
    }

    @After
    public void after(){
        sqlSession.close();
    }

    @Test
    public void testFindUserById(){
        Movie movie = movieManyMapper.findUserByMovieId(15);
        logger.debug("movie:{}", movie);
    }

    @Test
    public void testFindUserTypeByMovieId(){
        Movie movie = movieManyMapper.findUserTypeByMovieId(15);
        logger.debug("movie:{}", movie);
    }

    @Test
    public void testAddTypeBatch(){
        Type type = new Type();
        type.setTypeName("强盗");

        Type type1 = new Type();
        type1.setTypeName("歌舞");

        List<Type> typeList = Arrays.asList(type,type1);
        int count = movieManyMapper.addTypeBatch(typeList);
        logger.debug("count:{}", count);
        sqlSession.commit();
    }




















}
