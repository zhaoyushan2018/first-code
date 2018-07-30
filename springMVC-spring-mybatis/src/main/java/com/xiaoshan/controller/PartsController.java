package com.xiaoshan.controller;

import com.github.pagehelper.PageInfo;
import com.xiaoshan.entity.Parts;
import com.xiaoshan.entity.Type;
import com.xiaoshan.exception.NotFoundException;
import com.xiaoshan.service.PartsService;
import com.xiaoshan.service.TypeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.jws.WebParam;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author YushanZhao
 * @Date:2018/7/23
 */
@Controller
@RequestMapping("/parts")
public class PartsController {

    @Autowired
    private PartsService partsService;

    @Autowired
    private TypeService typeService;


    /*  partsTypeList-------------------------------------------------------------------------------------------------- */

    @GetMapping("/partsTypeList")
    public String partsTypeList(@RequestParam(name = "p", defaultValue = "1", required = false) Integer pageNo,
                                Model model){
        //List<Type> typeList = partsService.findTypeList();
        PageInfo<Type> typePage = typeService.findTypePageList(pageNo);

        model.addAttribute("typePage", typePage);

        return "parts/partsTypeList";
    }


    /*  parts-------------------------------------------------------------------------------------------------- */
    @GetMapping("/{id:\\d+}/edit")
    public String partsEdit(@PathVariable Integer id, Model model){
        //修改时先根据id查询出来对象  返回前端页面
        Parts parts = partsService.findPartsById(id);
        //封装typeList集合返回前端
        List<Type> typeList = partsService.findTypeList();
        model.addAttribute("typeList", typeList);
        model.addAttribute("parts", parts);
        return  "/parts/edit";
    }

    @PostMapping("/{id:\\d+}/edit")
    public String partsEdit(Parts parts, RedirectAttributes redirectAttributes){
        partsService.editParts(parts);
        redirectAttributes.addFlashAttribute("message","更新成功...");

        return "redirect:/parts";
    }

    @GetMapping("/{id:\\d+}/del")
    public String partsDel(@PathVariable Integer id,RedirectAttributes redirectAttributes){
        partsService.delPartsById(id);
        redirectAttributes.addFlashAttribute("message","删除成功...");
        return "redirect:/parts";
    }

    @GetMapping("/checkPartsNo")
    @ResponseBody
    public String checkPartsNo(String partsNo){
        //System.out.println(partsNo);
        List<Parts> partsList = partsService.findPartsByPartsNo(partsNo);
        if(partsList.isEmpty()){
            return "true";
        } else {
            return "false";
        }
    }

    @GetMapping("/new")
    public String partsNew(Model model){
        //封装typeList集合返回前端
        List<Type> typeList = partsService.findTypeList();
        model.addAttribute("typeList", typeList);

        return "parts/new";
    }

    //1.参数里面挨个接受   2.对象接受,属性要一样
    //提示信息 参数里面 用RedirectAttributes里面的addFlashAttribute()方法  刷新就没有了
    @PostMapping("/new")
    public String partsNew(Parts parts, RedirectAttributes redirectAttributes){
        partsService.saveParts(parts);
        //往前端传递 提示信息
        redirectAttributes.addFlashAttribute("message","入库成功...");

        //重定向到list列表
        return "redirect:/parts";
    }

    //required:必填项  false:可填可不填
    @GetMapping
    public String list(@RequestParam(name = "p", defaultValue = "1", required = false) Integer pageNo,
                       @RequestParam(required = false) String inventory,
                       @RequestParam(required = false) String partsName,
                       @RequestParam(required = false) Integer partsType,
                       Model model){
        //封装筛选的queryMap集合
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("partsName",partsName);
        queryMap.put("partsType", partsType);
        if (StringUtils.isNumeric(inventory)){
            queryMap.put("inventory", inventory);
        }

        //PageInfo<Parts> page = partsService.findPage(pageNo);
        PageInfo<Parts> page = partsService.findPageByPageNoAndQeryMap(pageNo, queryMap);

        List<Type> typeList = partsService.findTypeList();

        model.addAttribute("page",page);
        model.addAttribute("typeList",typeList);

        return "parts/list";
    }

    @GetMapping("/add")
    public String addParts(){
        return "parts/add";
    }
    @PostMapping("/add")
    public String addParts(Parts parts){
        partsService.addParts(parts);

        return "redirect:/parts/list";
    }

    @GetMapping("/delete")
    public String deleteParts(){
        return "parts/delete";
    }
    @PostMapping("/delete")
    public String deleteParts(@PathVariable Integer id){
        partsService.deletePartsById(id);

        return "parts/delete";
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
