package com.xiaoshan.controller;

import com.xiaoshan.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.jws.soap.SOAPBinding;
import java.io.UnsupportedEncodingException;
import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.List;

/**
 * @author YushanZhao
 * @Date:2018/7/22
 */
//在spring4.x+加入了@RestController 注解代替@Controller，那么每个方法都默认加上了@ResponseBody注解
@Controller
//@RestController
@RequestMapping("/user") //相当于下面每个请求前面都加了
public class UserController {

    @GetMapping
    public String index(){
        return "index";
    }

    //@GetMapping("/user/hello")
   /* @GetMapping("/hello")
    public void hello(){
        System.out.println("hello,springMVC.../...UserController");
    }*/

    /*@GetMapping("/add")
    public String addUser(){
        System.out.println("添加一个User用户");
        return "add";
    }*/

    /*@GetMapping("/{id:\\d+}")
    public void showUserId(@PathVariable Integer id){
        System.out.println("UserId: " + id);
    }*/

   /* @GetMapping("/{id:\\d+}")
   public void showUserId(@PathVariable Integer id){
       System.out.println("userId: " + id);
   }*/

    /*@GetMapping("/{type:v-\\d+}/{id:\\d+}")
    public void showUserIdType(@PathVariable String type,@PathVariable Integer id){
        System.out.println("获取多个参数");
        System.out.println("type: " + type);
        System.out.println("userId: " + id);
    }*/

    /*@GetMapping("/{type:v-\\d+}/{id:\\d+}")
    public String showUserIdType1(@PathVariable String type, @PathVariable Integer id, Model model){

        model.addAttribute("userId",id);
        model.addAttribute("userName","xiaoli");

        System.out.println("获取多个参数");
        System.out.println("type: " + type);
        System.out.println("userId: " + id);
        return "home";
    }*/

   /* @GetMapping("/{type:v-\\d+}/{id:\\d+}")
    public String showTypeId(@PathVariable String type,@PathVariable Integer id, Model model){
        model.addAttribute("userId",id);
        model.addAttribute("userName","baby");

        System.out.println("id: " + id);
        System.out.println("type: " + type);
        return "home";
    }*/

   /*@GetMapping("/{type:v-\\d+}/{id:\\d+}")
    public ModelAndView showIdType(@PathVariable String type, @PathVariable Integer id){
       ModelAndView modelAndView = new ModelAndView();
       modelAndView.setViewName("home");
       modelAndView.addObject("userId", id);
       modelAndView.addObject("userName","tom");

       System.out.println("id: " + id);
       System.out.println("type: " + type);
       return modelAndView;
    }*/
   /*@GetMapping("/{type:v-\\d+}/{id:\\d+}")
   public ModelAndView showIdType(@PathVariable String type, @PathVariable Integer id){
       ModelAndView modelAndView = new ModelAndView("home");
       modelAndView.addObject("userId", id);
       modelAndView.addObject("userName","tom");

       System.out.println("id: " + id);
       System.out.println("type: " + type);
       return modelAndView;
   }*/


    /*@RequestMapping(value = "/{type:v-\\d+}/{id:\\d+}", method = RequestMethod.GET)
    public String showTypeIdAndP(@PathVariable String type,
                               @PathVariable Integer id,
                               String p,
                               Model model){

        model.addAttribute("userId",id);
        model.addAttribute("userName",p);
        System.out.println("type:" + type);
        System.out.println("id:" + id);
        System.out.println("p:" + p);
        return "home";
    }*/

    /*@RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String showP(String p, Model model){
        model.addAttribute("p",p);
        System.out.println("p:" + p);
        return "home";
    }*/

    @GetMapping("/{id:\\d+}")
    public String showP(@PathVariable Integer id,
                        @RequestParam(name = "p", defaultValue = "1") Integer pageNo){
        System.out.println("id:" + id);
        System.out.println("p:" + pageNo);
        return "home";
    }

   /* @RequestMapping(value = "/{id:\\d+}", method = RequestMethod.GET)
    public String showP(@PathVariable Integer id,//没写p的时候默认值
                        @RequestParam(defaultValue = "1") Integer p){
        System.out.println("id:" + id);
        System.out.println("p:" + p);
        return "home";
    }*/

  /* @GetMapping("/add")
    public String add(){
        return "add";
    }

    @PostMapping("/add")
    public String add(String name, String address){
        System.out.println("name:" + name);
        System.out.println("address:" + address);
        return "redirect:/user/home";
    }

    @GetMapping("/home")
    public String goHome(){
       return "home";
    }  */

    /*@GetMapping("/add")
    public String add(){
        return "add";
    }

    @PostMapping("/add")
    public String add(User user, String tell){ //实体类没封装的就在后面加
        System.out.println("name:" + user.getName());
        System.out.println("address:" + user.getAddress());
        System.out.println("tell:" + tell);
        return "redirect:/user/home";
    }

    @GetMapping("/home")
    public String goHome(){
        return "home";
    }*/

  /*  @GetMapping("/{type:v-\\d+}/{id:\\d+}")
    public ModelAndView showTypeId(@PathVariable String type,
                             @PathVariable Integer id,
                             @RequestParam(defaultValue = "1") Integer p,
                                   String name){
//        try {
//            name = new String(name.getBytes("ISO8859-1"),"UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }

        System.out.println("type:" + type);
        System.out.println("id:" + id);
        System.out.println("p:" + p);
        System.out.println("name:" + name);

        ModelAndView modelAndView = new ModelAndView("home");
        modelAndView.addObject("userId",id);
        modelAndView.addObject("userName",type);
        modelAndView.addObject("p",p);

        return modelAndView;
    }*/


    @GetMapping("/{type:v-.+}/{id:\\d+}")
    public ModelAndView showTypeId(@PathVariable String type,
                                   @PathVariable Integer id,
                                   @RequestParam(defaultValue = "1") Integer p,
                                   String name){
//        try {
//            name = new String(name.getBytes("ISO8859-1"),"UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }

        System.out.println("type:" + type);
        System.out.println("id:" + id);
        System.out.println("p:" + p);
        System.out.println("name:" + name);

        ModelAndView modelAndView = new ModelAndView("home");
        modelAndView.addObject("userId",id);
        modelAndView.addObject("userName",type);
        modelAndView.addObject("p",p);

        return modelAndView;
    }

    @GetMapping(value = "/save", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String save(){
        System.out.println("save success...");
        return "保存成功,恭喜恭喜";
    }


    @GetMapping("show")
    @ResponseBody
    public User showUser(){
        User user = new User();
        user.setId(1);
        user.setName("xiaoli");
        user.setAddress("xian");
        return user;
    }

    @GetMapping("/showAll")
    @ResponseBody
    public List<User> showAllUser(){
        List<User> userList = Arrays.asList(
                new User(1,"huanhuan","xinxiang"),
                new User(2,"tingting","xiana"),
                new User(3,"zhengzheng","xian")
        );
        return userList;
    }

}
