package com.xiaoshan.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author YushanZhao
 * @Date:2018/7/19
 */
@Controller
public class HelloController {

    //@RequestMapping(value = "/hello", method = RequestMethod.GET)支持一个get
    //@RequestMapping(value = "/hello", method = {RequestMethod.GET,RequestMethod.POST})支持两个get,post
    @GetMapping("/hello")
    @PostMapping("/hello")
    public String hello(){
        System.out.println("hello,SpringMvc");
        return "hello";
    }

}
