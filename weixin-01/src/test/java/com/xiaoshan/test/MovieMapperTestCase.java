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

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author YushanZhao
 * @Date:2018/7/11
 */
public class MovieMapperTestCase {

    Logger logger = LoggerFactory.getLogger(MovieMapperTestCase.class);
    private SqlSession sqlSession;
    private MovieMapper movieMapper;

    @Before
    public void before(){
        sqlSession = MyBatisUtils.getSqlSession();
        //动态代理:sqlSession对象根据接口的class动态创建接口的实现类
        movieMapper = sqlSession.getMapper(MovieMapper.class);//获得接口的实现类对象
    }

    @After
    public void after(){
        sqlSession.close();
    }

    @Test
    public void testFindMovieByKey(){
        List<Movie> movieList = movieMapper.findMovieByKey("%西%");
        for (Movie movie : movieList){
            logger.debug("Movie:{}", movie);
        }
    }

    @Test
    public void testFindMovieByIdList(){
        List<Integer> idList = Arrays.asList(15,25,35);
        List<Movie> movieList = movieMapper.findMovieByIdList(idList);
        for (Movie movie : movieList){
            logger.debug("movie:{}", movie);
        }
    }

    @Test
    public void testFindByParam(){
        Map<String, Object> maps = new HashMap<>();
        maps.put("movieNameKey","%1%");
        maps.put("directorNameKey", "%小%");

        List<Movie> movieList = movieMapper.findByParam(maps);
        for (Movie movie : movieList){
            logger.debug("movie:{}", movie);
        }

    }


























}
