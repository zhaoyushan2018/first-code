package com.xiaoshan.test;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiaoshan.entity.Movieone;
import com.xiaoshan.entity.MovieoneExample;
import com.xiaoshan.mapper.MovieoneMapper;
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
public class MovieoneMapperTestCase {

    Logger logger = LoggerFactory.getLogger(MovieoneMapperTestCase.class);

    private SqlSession sqlSession;
    private MovieoneMapper movieoneMapper;

    @Before
    public void before(){
        sqlSession = MyBatisUtils.getSqlSession();
        // 动态代理：sqlSession对象根据接口的class动态创建接口的实现类
        // 获得接口的实现类对象
        movieoneMapper = sqlSession.getMapper(MovieoneMapper.class);
    }

    @After
    public void after(){
        sqlSession.close();
    }

    @Test
    public void testInsert(){
        Movieone movieone = new Movieone();
        movieone.setMovieName("逆战");
        movieone.setDirectorName("林超贤");
        movieone.setUserId(6);

        int count = movieoneMapper.insert(movieone);
        logger.debug("count:{}", count);

        sqlSession.commit();
    }

    @Test
    public void testSelectByExample(){

        //创建MovieoneExample实体类   直接当参数时查询全部
        MovieoneExample movieoneExample = new MovieoneExample();
        //movieoneExample.createCriteria();  criteria.and...相当于加条件
        /*MovieoneExample.Criteria criteria = movieoneExample.createCriteria();
        criteria.andMovieNameLike("%1%");
        criteria.andUserIdEqualTo(6);*/
        //相当以上方三行
        movieoneExample.createCriteria().andMovieNameLike("%1%").andUserIdEqualTo(6);

        List<Movieone> movieoneList = movieoneMapper.selectByExample(movieoneExample);
        for(Movieone movieone : movieoneList){
            logger.debug("movieone:{}", movieone);
        }
    }

    @Test
    public void testSelectByExample1(){
        MovieoneExample movieoneExample = new MovieoneExample();
        movieoneExample.createCriteria().andMovieNameLike("%1%");
        movieoneExample.setOrderByClause("id desc");//按照id降序排列
        movieoneExample.setDistinct(true); //是否去重

        List<Movieone> movieoneList = movieoneMapper.selectByExample(movieoneExample);
        for(Movieone movieone : movieoneList){
            System.out.println(movieone);
        }


        //select All 查询全部
       /* MovieoneExample movieoneExample = new MovieoneExample();

        List<Movieone> movieoneList = movieoneMapper.selectByExample(movieoneExample);
        for(Movieone movieone : movieoneList){
            System.out.println(movieone);
        }*/
    }

    @Test
    public void testSelectByPrimaryKey(){
        Movieone movieone = movieoneMapper.selectByPrimaryKey(48);
        logger.debug("movieone:{}", movieone);
    }

    @Test
    public void testDeleteByPrimaryKey(){
        int a = movieoneMapper.deleteByPrimaryKey(31);
        System.out.println(a);

        sqlSession.commit();
    }

    @Test
    public void testDeleteByExample(){
        MovieoneExample movieoneExample = new MovieoneExample();
        movieoneExample.createCriteria().andMovieNameLike("%狸%");

        int a = movieoneMapper.deleteByExample(movieoneExample);

        System.out.println(a);
        sqlSession.commit();
    }

    @Test
    public void testUpdateByPrimaryKey(){
        Movieone movieone = movieoneMapper.selectByPrimaryKey(49);
        movieone.setDirectorName("张杰");
        movieone.setUserId(4);

        int a = movieoneMapper.updateByPrimaryKey(movieone);

        System.out.println(a);
        sqlSession.commit();
    }

    @Test
    public void testUpdateByPrimaryKeySelective(){
        Movieone movieone = new Movieone();
        movieone.setId(49);
        movieone.setMovieName("逆战逆战来也");

        int a = movieoneMapper.updateByPrimaryKeySelective(movieone);
        System.out.println(a);
        sqlSession.commit();
    }

    @Test
    public void testPage(){

        //第一页，取5条数据
        PageHelper.startPage(1,5);
        MovieoneExample movieoneExample = new MovieoneExample();
        List<Movieone> movieoneList = movieoneMapper.selectByExample(movieoneExample);

        for(Movieone movieone : movieoneList){
            System.out.println(movieone);
        }
    }

    @Test
    public void testPlayPage(){
        //第一页,取5条数据.  pageNum当前页码  每页显示数量pageSize
       // PageHelper.startPage(1, 5);

        //直接创建movieoneExample当参数,为查询全部
       /* MovieoneExample movieoneExample = new MovieoneExample();
        List<Movieone> movieoneList = movieoneMapper.selectByExample(movieoneExample);

        for (Movieone movieone : movieoneList){
            System.out.println(movieone);
        }*/

       //从第3页开始,每页5条数据
       /* PageHelper.startPage(3,5);
        MovieoneExample movieoneExample = new MovieoneExample();
        List<Movieone> movieoneList = movieoneMapper.selectByExample(movieoneExample);

        for (Movieone movieone : movieoneList){
            System.out.println(movieone);
        }*/

       //从5开始,取10条数据(从第5条数据开始,取10条数据)
       /* PageHelper.offsetPage(5,10);
        MovieoneExample movieoneExample = new MovieoneExample();
        List<Movieone> movieoneList = movieoneMapper.selectByExample(movieoneExample);

        for (Movieone movieone : movieoneList){
            System.out.println(movieone);
        }*/

       //从第3页开始,每页6条数据
        PageHelper.startPage(3,6);
        MovieoneExample movieoneExample = new MovieoneExample();
        List<Movieone> movieoneList = movieoneMapper.selectByExample(movieoneExample);

        //转换为PageInfo对象
        PageInfo<Movieone> pageInfo = new PageInfo<>(movieoneList);
        for (Movieone movieone : pageInfo.getList()){
            System.out.println(movieone);
        }

    }






}
