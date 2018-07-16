package com.xiaoshan.dao;

import org.springframework.stereotype.Repository;

import javax.inject.Named;

/**
 * @author YushanZhao
 * @Date:2018/7/16
 */
//@Named
//@Repository(value = "userDao")
@Repository("userDao")
public class UserDao {   // java标准规范的编号

    public void save(){
        System.out.println("user save success...");
    }

}
