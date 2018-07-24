package com.xiaoshan.controller;

import com.github.pagehelper.PageInfo;
import com.xiaoshan.entity.Parts;
import com.xiaoshan.exception.NotFoundException;
import com.xiaoshan.service.PartsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.jws.WebParam;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author YushanZhao
 * @Date:2018/7/23
 */
@Controller
@RequestMapping("/parts")
public class PartsController {

    @Autowired
    private PartsService partsService;

    //required必填项
    @GetMapping
    public String list(@RequestParam(name = "p", defaultValue = "1", required = false) Integer pageNo, Model model){
        PageInfo<Parts> page = partsService.findPage(pageNo);
        model.addAttribute("page",page);

        return "parts/list";
    }


    /*@GetMapping("/{id:\\d+}") //第一种
    public String findPartsById(@PathVariable Integer id, HttpServletResponse resp, Model model) throws IOException {
        Parts parts = partsService.findPartsById(id);
        if(parts == null){
            //抛异常
            resp.sendError(404, "资源未找到");
            return null;
        } else {
            model.addAttribute("parts",parts);
            return "parts/detail";
        }
    }*/

    /*@GetMapping("/{id:\\d+}") //第二种,(src/java/com.xiaoshan/exception/NotFoundException)
    public String findPartsById(@PathVariable Integer id, Model model){
        Parts parts = partsService.findPartsById(id);
        if(parts == null){
            //抛异常
            throw new NotFoundException();
        } else {
            model.addAttribute("parts",parts);
        }
        return "parts/detail";
    }*/

   /* @GetMapping("/{id:\\d+}") //异常处理,常见的(views/error/500.jsp)跳转错误页面
    public String findPartsById(@PathVariable Integer id, Model model){
        Parts parts = partsService.findPartsById(id);
        if(parts == null){
            //抛异常
            return "error/500";
        } else {
            model.addAttribute("parts",parts);
            return "parts/detail";
        }
    }*/

    @GetMapping("/{id:\\d+}") //异常处理,(全局的){加一个exception/ControllerExceptionHandler}
    public String findPartsById(@PathVariable Integer id, Model model) throws IOException {
        Parts parts = partsService.findPartsById(id);
        if(parts == null){
            //抛异常
            throw new IOException();
        } else {
            model.addAttribute("parts",parts);
            return "parts/detail";
        }
    }



}
