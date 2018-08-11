package com.xiaoshan.service.impl;

import com.xiaoshan.service.UserService;

/**
 * @author YushanZhao
 * @Date:2018/8/11
 */
public class UserServiceImpl implements UserService {
    public String findById(Integer id) {
        if(id.equals(1)){
            return "huanhuan";
        } else if(id.equals(2)){
            return "xiaoli";
        } else {
            return "zhenzhen";
        }

    }
}
