package com.xiaoshan.dao;

/**
 * @author YushanZhao
 * @Date:2018/7/13
 */
public class MovieDao {

    public  MovieDao(){
        System.out.println("create MovieDao...");
    }

    public void init(){
        System.out.println("init ...");
    }

    public void destroy(){
        System.out.println("destroy ...");
    }


    public void save(){
        System.out.println("save success !!!");
    }

}
