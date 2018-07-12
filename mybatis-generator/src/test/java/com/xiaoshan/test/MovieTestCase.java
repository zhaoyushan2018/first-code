package com.xiaoshan.test;

import com.xiaoshan.entity.Movie;
import com.xiaoshan.mapper.MovieMapper;
import com.xiaoshan.util.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author YushanZhao
 * @Date:2018/7/12
 */
public class MovieTestCase {

    Logger logger = LoggerFactory.getLogger(MovieTestCase.class);

    private SqlSession sqlSession;
    private MovieMapper movieMapper;

    @Before
    public void before(){
        sqlSession = MyBatisUtils.getSqlSession();
        // 动态代理：sqlSession对象根据接口的class动态创建接口的实现类
        // 获得接口的实现类对象
        movieMapper = sqlSession.getMapper(MovieMapper.class);
    }

    @After
    public void after(){
        sqlSession.close();
    }

    @Test
    public void testInsert(){
        Movie movie = new Movie();
        movie.setMovieName("热血警探");
        movie.setDirectorName("karen");
        movie.setUserId(11);

        int count = movieMapper.insert(movie);
        logger.debug("count:{}", count);

        sqlSession.commit();
    }

    @Test
    public void testDeleteByPrimaryKey(){
        int count = movieMapper.deleteByPrimaryKey(44);
        logger.debug("count:{}", count);

        sqlSession.commit();
    }

    @Test
    public void testSelectByPrimaryKey(){
        Movie movie = movieMapper.selectByPrimaryKey(45);
        logger.debug("movie:{}", movie);
    }

    @Test
    public void testSelectAll(){
        List<Movie> movieList = movieMapper.selectAll();
        for(Movie movie : movieList){
            logger.debug("movie:{}", movie);
        }
    }

    @Test
    public void testUpdateByPrimaryKey(){
        Movie movie = new Movie();
        movie.setId(46);
        movie.setUserId(4);
        movie.setMovieName("数据库");

        int i = movieMapper.updateByPrimaryKey(movie);
        logger.debug("i:{}", i);

        sqlSession.commit();
    }

}
