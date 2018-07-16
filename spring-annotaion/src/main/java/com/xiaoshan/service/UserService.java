package com.xiaoshan.service;

import com.xiaoshan.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;

import javax.inject.Named;

/**
 * @author YushanZhao
 * @Date:2018/7/16
 */
@Named
//@Scope("protptype")//把单例变成非单例模式
//@Lazy//变成懒加载模式
public class UserService {

    private UserDao userDao;

    @Autowired
    public UserService(UserDao userDao){
        this.userDao = userDao;
    }

    public void save(){
        //int a = 10 / 0 ;
        userDao.save();
    }


  /* - - - - - - - - - - - -- - - - - - - -- - - - - - - - - - - -- - - - - */
    /*private UserDao userDao;

    *//* 不规范,同上,上面规范 *//*
    public UserService(UserDao userDao){
        this.userDao = userDao;
    }

    public void save(){
        userDao.save();
    }*/

 /* - - - - - - - - - - - -- - - - - - - -- - - - - - - - - - - -- - - - - */
    /*@Autowired
    private UserDao userDao;

    public void save(){
        userDao.save();
    }*/

    /*@Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }*/

/* - - - - - - - - - - - -- - - - - - - -- - - - - - - - - - - -- - - - - */
   /* 1.@Named
//@Scope("protptype")//把单例变成非单例模式
//@Lazy//变成懒加载模式
    public class UserService {

        private UserDao userDao;

        public void save(){
            userDao.save();
        }

        @Autowired
        public void setUserDao(UserDao userDao) {
            this.userDao = userDao;
        }1.*/
}
