package com.xiaoshan.controller;

import com.xiaoshan.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author YushanZhao
 * @Date:2018/7/20
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @GetMapping("/add")
    public String addUser(){
        System.out.println("添加一个用户...");
        return "user/add";
    }

    @GetMapping("/delete")
    public String deleteUser(){
        System.out.println("删除一个user用户");
        return "user/delete";
    }

   /* @GetMapping("/{id}")
    public void showUserId(@PathVariable Integer id){
        System.out.println("根据id获得User,id: " + id);
    }*/
   /* @GetMapping(value = "/{id}")
    public String showUserId(@PathVariable(name = "id") Integer Userid){
        System.out.println("根据id获得User,id: " + Userid);
        return "user/home";
    }*/
   /* @GetMapping(value = "/{id://d+}")  //约束id,可以输入多个正整数
    public String showUserId(@PathVariable(name = "id") Integer Userid){
        System.out.println("根据id获得User,id: " + Userid);
        return "user/home";
    }*/

    //定义多个参数
    @GetMapping(value = "/{type:v-\\d+}/{id:\\d+}")
    public String showUserId(
            @PathVariable String type,
            @PathVariable Integer id){
        System.out.println("根据id获得User,id: " + id);
        System.out.println("type: " + type);
        return "user/home";
    }

    /*@GetMapping("/{id}")
    public String showUserId(@PathVariable Integer id, Model model){
        model.addAttribute("userId",id);
        System.out.println("根据id获得User,id: " + id);
        return "user/home";
    }*/
    /*@GetMapping("/{id:\\d+}")
    public ModelAndView showUserId(@PathVariable Integer id){

        *//*ModelAndView modelAndView = new ModelAndView();
        //视图设置
        modelAndView.setViewName("user/home");*//*

        ModelAndView modelAndView = new ModelAndView("user/home");

        modelAndView.addObject("userId", id);
        modelAndView.addObject("address","china");
        System.out.println("根据id获得User,id: " + id);
        return modelAndView;
    }*/

    @GetMapping("/{id:\\d+}")
    public String show(Integer p, @PathVariable Integer id, Model model){
        model.addAttribute("address: ","china");
        System.out.println("p: " + p);
        System.out.println("id:"+ id );

        return "user/home";
    }





}
