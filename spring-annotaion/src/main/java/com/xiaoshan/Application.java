package com.xiaoshan;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * @author YushanZhao
 * @Date:2018/7/17
 */
@Configuration
@ComponentScan
@EnableAspectJAutoProxy
//读取配置文件
@PropertySource("classpath:config.properties")
public class Application {

    @Autowired
    private Environment environment;

    @Bean
    public DataSource dataSource(){
        BasicDataSource basicDataSource= new BasicDataSource();
        basicDataSource.setDriverClassName(environment.getProperty("jdbc.driver"));
        basicDataSource.setUrl(environment.getProperty("jdbc.url"));
        basicDataSource.setUsername(environment.getProperty("jdbc.username"));
        basicDataSource.setPassword(environment.getProperty("jdbc.password"));

        return basicDataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(){
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource());

        return jdbcTemplate;
    }


     //application.java == spring.xml 完全替代
    //配置数据库连接池
    //将返回值放入Spring容器
   /* @Bean
    public DataSource dataSource(){
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        basicDataSource.setUrl("jdbc:mysql:///test");
        basicDataSource.setUsername("root");
        basicDataSource.setPassword("root");

        return basicDataSource;
    }*/
    //配置数据库工具
   /* @Bean
    public JdbcTemplate jdbcTemplate(){
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource());

        return jdbcTemplate;
    }*/




}
