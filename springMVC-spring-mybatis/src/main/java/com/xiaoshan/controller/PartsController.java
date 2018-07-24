package com.xiaoshan.controller;

import com.xiaoshan.entity.Parts;
import com.xiaoshan.service.PartsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.jws.WebParam;
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

    @GetMapping("/{id:\\d+}")
    public String findPartsById(@PathVariable Integer id, Model model) throws IOException {
        Parts parts = partsService.findPartsById(id);
        if(parts == null){
            //抛异常
            throw new IOException();
        } else {
            model.addAttribute("parts",parts);
        }
        return "parts/detail";
    }

}
