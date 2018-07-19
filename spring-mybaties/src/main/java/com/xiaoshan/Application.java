package com.xiaoshan;

import org.apache.commons.dbcp2.BasicDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;

/**
 * @author YushanZhao
 * @Date:2018/7/19
 */

//@Configuration标记为spring的java配置类
//@ComponentScan开启自动扫描,默认从当前类所在包及其子包开始扫描,也可以通过basePackages设置扫描路径
// 读取配置文件 @PropertySource
//开启基于注解的(AOP)  @EnableAspectJAutoProxy
// 自动扫描mapper接口并创建实现类对象加入spring容器 @MapperScan

@Configuration
@ComponentScan
@PropertySource("classpath:config.properties")
//@EnableAspectJAutoProxy
@MapperScan(basePackages = "com.xiaoshan.mapper")
public class Application {

    @Autowired
    private Environment environment;

    @Bean
    public DataSource dataSource(){
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName(environment.getProperty("jdbc.driver"));
        basicDataSource.setUrl(environment.getProperty("jdbc.url"));
        basicDataSource.setUsername(environment.getProperty("jdbc.username"));
        basicDataSource.setPassword(environment.getProperty("jdbc.password"));
        return basicDataSource;
    }

    //配置jdbc事务管理器
    @Bean
    public DataSourceTransactionManager dataSourceTransactionManager(DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }

    //配置sqlSessionFactoryBean
    @Bean
    public SqlSessionFactoryBean sqlSessionFactoryBean(DataSource dataSource) throws IOException {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        //设置数据库连接池
        sqlSessionFactoryBean.setDataSource(dataSource);
        //配置别名所在的包，别名默认为类名
        sqlSessionFactoryBean.setTypeAliasesPackage("com.xiaoshan.entity");
        //配置Mapper XML所在的位置
        ResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = patternResolver.getResources("classpath:mapper/*.xml");
        sqlSessionFactoryBean.setMapperLocations(resources);

        //mybatis的其他配置
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setMapUnderscoreToCamelCase(true);
        sqlSessionFactoryBean.setConfiguration(configuration);

        return sqlSessionFactoryBean;
    }

}
