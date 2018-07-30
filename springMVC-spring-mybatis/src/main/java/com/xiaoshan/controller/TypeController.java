package com.xiaoshan.controller;

import com.xiaoshan.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @author YushanZhao
 * @Date:2018/7/25
 */
@Controller
@RequestMapping("/type")
public class TypeController {

    @Autowired
    private TypeService typeService;

    @GetMapping("/{id:\\d+}/del")
    public String typeDel(@PathVariable Integer id, RedirectAttributes redirectAttributes){
        typeService.delTypeById(id);

        redirectAttributes.addFlashAttribute("message", "配件类型删除成功...");
        return "redirect:/parts/partsTypeList";
    }

}
