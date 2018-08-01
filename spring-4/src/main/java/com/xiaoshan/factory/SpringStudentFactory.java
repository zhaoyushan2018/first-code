package com.xiaoshan.factory;

import org.springframework.beans.factory.FactoryBean;

/**
 * @author YushanZhao
 * @Date:2018/7/31
 */
public class SpringStudentFactory implements FactoryBean<Student> {

    /**
     * 工厂产生的bean, 具体的产品  泛型的bean
     * @return
     * @throws Exception
     */
    @Override
    public Student getObject() throws Exception {
        return new Student();
    }

    /**
     * 什么类型
     * @return
     */
    @Override
    public Class<?> getObjectType() {
        return Student.class;
    }

    /**
     * 是否单例
     * @return
     */
    @Override
    public boolean isSingleton() {
        return true;
    }
}
