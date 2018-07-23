package com.xiaoshan.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author YushanZhao
 * @Date:2018/7/22
 */
@Controller
public class HelloController {


    //@RequestMapping("/hello") //任意请求
    //@RequestMapping(value = "/hello", method = RequestMethod.GET)  //只允许一个
    //@RequestMapping(value = "/hello", method = {RequestMethod.GET,RequestMethod.POST}) //支持多个
    //@GetMapping("/hello") //支持一个get
    @PostMapping("/hello")
    public void hello(){
        System.out.println("hello,springmvc.../...HelloController");
    }

}
