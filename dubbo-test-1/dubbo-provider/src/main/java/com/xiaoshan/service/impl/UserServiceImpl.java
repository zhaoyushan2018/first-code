package com.xiaoshan.service.impl;

import com.xiaoshan.service.UserService;

/**
 * @author YushanZhao
 * @Date:2018/8/12
 */
public class UserServiceImpl implements UserService {


    public String findUserNameById(Integer id) {
        if(id.equals(1)){
            return "xiaoli";
        } else if(id.equals(2)){
            return "huanhuan";
        } else {
            return "zhenzhen";
        }

    }
}
