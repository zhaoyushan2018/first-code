package com.xiaoshan.util;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;

/**
 * @author YushanZhao
 * @Date:2018/7/12
 */
public class MyBatisUtils {

    private static SqlSessionFactory sqlSessionFactory;

    static{
        //读取配置文件
        Reader reader = null;
        try {
            reader = Resources.getResourceAsReader("mybatis.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }

        //创建sqlSessionFactory
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);

    }

    public static SqlSessionFactory getSqlSessionFactory(){
        return sqlSessionFactory;
    }

    public static SqlSession getSqlSession(boolean autoCommit){
        return getSqlSessionFactory().openSession(autoCommit);
    }

    public static SqlSession getSqlSession(){
        return getSqlSessionFactory().openSession();
    }

}
