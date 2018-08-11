package com.xiaoshan.erp.controller;

import com.xiaoshan.erp.service.EmployeeService;
import com.xiaoshan.erp.util.Constant;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 首页控制器,跳转首页,登录.登出系统
 * @author YushanZhao
 * @Date:2018/7/26
 */
@Controller
public class HomeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/home")
    public String home(){

        return "home";
    }

    @GetMapping("/")
     public String login(){
        //创建subject主体对象
        Subject subject = SecurityUtils.getSubject();

        //判断当前是否已经通过认证, 如果通过则退出登录
        if(subject.isAuthenticated()){
            subject.logout();
        }

        //判断当前登录状态,如果是已记住我,则直接跳转首页,跳过登录
        if(subject.isRemembered()){
            return "home";
        }

        //System.out.println(subject.isAuthenticated()); //是否认证(登录) (这两个只会有一个为true)
        //System.out.println(subject.isRemembered());   //是否记住


        return "login";
     }

    @PostMapping("/")
    public String login(String loginTel,
                        String loginPassword,
                        String remember,
                        HttpServletRequest request,
                        RedirectAttributes redirectAttributes){


        if (loginTel == null){
            return "redirect:/";
        }

        System.out.println("HomeController" + loginTel);


        //创建subject主体对象
        Subject subject = SecurityUtils.getSubject();
        //获得登录ip
        String loginIp = request.getRemoteAddr();
        //通过userTel.password封装UsernamePasswordToken对象进行登录
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(loginTel, DigestUtils.md5Hex(loginPassword + Constant.DEFAULT_SALT), remember != null, loginIp);

        try {
            subject.login(usernamePasswordToken);

            //判断是否有有车辆服务的权限
            if(subject.hasRole("car")){
                //判断跳转路径 (获得登陆前的路径)
                SavedRequest savedRequest = WebUtils.getSavedRequest(request);
                String url = "/home";
                if(savedRequest != null){
                    //获得callback的url
                    url = savedRequest.getRequestUrl();
                }
                return "redirect:" + url;
            } else {
                redirectAttributes.addFlashAttribute("message", "您没有登录该系统的权限");
            }



        } catch (UnknownAccountException |IncorrectCredentialsException e) {
            redirectAttributes.addFlashAttribute("message", "用户名或者密码错误...");
        } catch (LockedAccountException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        } catch (AuthenticationException e) {
            redirectAttributes.addFlashAttribute("message", "输入有误,重新输入");
        }
        //登录不成功,跳转登录页面
        return "redirect:/";


        /*//获得请求ip
        String loginIp = request.getRemoteAddr();

        try {
            Employee employee = employeeService.login(loginTel,loginPassword,loginIp);
            session.setAttribute("employee", employee);
            return "redirect:/home";
        } catch (ServiceException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/";
        }*/

    }

    @GetMapping("/401")
    public String unauthorizedUrl(){

        return "error/401";
    }


    /*@PostMapping("/")
    public String login(String loginTel,
                        String loginPassword,
                        String remember,
                        HttpSession session,
                        HttpServletRequest request,
                        RedirectAttributes redirectAttributes){
        //获得请求ip
        String loginIp = request.getRemoteAddr();

        try {
            Employee employee = employeeService.login(loginTel,loginPassword,loginIp);
            session.setAttribute("employee", employee);
            return "redirect:/home";
        } catch (ServiceException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/";
        }

    }*/

    /*@GetMapping("/logout")
    public void logout(HttpSession session){
        //调用invalidate()方法强制过期 退出时用
        session.invalidate();
    }*/


    //不写登出, 用自带的
   /* @GetMapping("/logout")
    public String loginout(RedirectAttributes redirectAttributes){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        redirectAttributes.addFlashAttribute("message", "已退出，请重新登录...");

        return "redirect:/";
    }*/

}
