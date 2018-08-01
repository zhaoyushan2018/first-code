package com.xiaoshan.erp.controller;

import com.google.common.collect.Lists;
import com.xiaoshan.erp.controller.exceptionHandler.NotFoundException;
import com.xiaoshan.erp.dto.ResponseBean;
import com.xiaoshan.erp.entity.Permission;
import com.xiaoshan.erp.exception.ServiceException;
import com.xiaoshan.erp.service.RolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * premission权限
 * 权限管理
 * @author YushanZhao
 * @Date:2018/7/27
 */
@Controller
@RequestMapping("/manage/permission")
public class PermissionController {

    @Autowired
    private RolePermissionService rolePermissionService;

    @GetMapping
    public String home(Model model){
        // 返回权限菜单集合 到前端展示
        List<Permission> permissionList = rolePermissionService.findAllPermission();
        model.addAttribute("permissionList", permissionList);

        System.out.println("开始----------------------------------------------------------------------------------------------------------");
        for(Permission permission : permissionList){
            System.out.println(permission);
        }

        return "manage/permission/home";
    }


    @GetMapping("/new")
    public String premissionAdd(Model model){
        // 返回权限菜单集合 到前端父权限列表
        List<Permission> menuPermissionList = rolePermissionService.findPermissionByType(Permission.PERMISSION_TYPE_MENU);
        model.addAttribute("menuPermissionList", menuPermissionList);

        return "manage/permission/new";
    }

    @PostMapping("/new")
    public String premissionAdd(Permission permission, RedirectAttributes redirectAttributes){
        rolePermissionService.savePermission(permission);
        redirectAttributes.addFlashAttribute("message", "权限保存成功...");

        return "redirect:/manage/permission";
    }


    /*@GetMapping("/{id:\\d+}/update")
    public String permissionUpdate(@PathVariable Integer id, Model model){
        try {
            //封装当前对象
            Permission permission = rolePermissionService.findPermissionById(id);
            model.addAttribute("permission", permission);

            //获取当前权限的所有子权限(菜单)
            List<Permission> sonPermissionList = rolePermissionService.findAllPermissionSon(permission);

            //封装所有权限列表(菜单)
            List<Permission> menuPermissionList = rolePermissionService.findPermissionByType(Permission.PERMISSION_TYPE_MENU);

            //排除当前权限和当前权限的子权限
            //移除当前对象的子权限
           if(sonPermissionList != null && sonPermissionList.size() > 0){
               for(Permission sonPermission : sonPermissionList){
                   for(Permission allPermission : menuPermissionList){
                       if(sonPermission.getId().equals(allPermission.getId())){
                           menuPermissionList.remove(sonPermission);
                           break;
                       }
                   }
               }
           }
            //移除当前对象
            menuPermissionList.remove(permission);
            model.addAttribute("menuPermissionList", menuPermissionList);
        }catch (ServiceException e){
            model.addAttribute("message", e.getMessage());
            throw new NotFoundException();
        }

        return "manage/permission/update";
    }*/
    @GetMapping("/{id:\\d+}/update")
    public String permissionUpdate(@PathVariable Integer id, Model model){
        //封装当前对象
        Permission permission = rolePermissionService.findPermissionById(id);
        if(permission == null){
            throw new NotFoundException();
        }

        //封装所有权限列表(菜单)
        List<Permission> menuPermissionList = rolePermissionService.findPermissionByType(Permission.PERMISSION_TYPE_MENU);
        //排除当前permission对象及其子权限对象
        remove(menuPermissionList, permission);

        model.addAttribute("menuPermissionList", menuPermissionList);
        model.addAttribute("permission", permission);
        return "manage/permission/update";
    }


    /**
     * 递归去除所有子权限
     * @param menuPermissionList 源List(全部权限菜单集合)
     * @param permission 要去除的权限对象
     */
    public void remove(List<Permission> menuPermissionList, Permission permission){
        //通过临时变量来存储所有的list元素防止漏删
        List<Permission> temp = Lists.newArrayList(menuPermissionList);

        for(int i = 0; i < temp.size(); i++){
            //判断有没有子权限要去除
            if(temp.get(i).getPid().equals(permission.getId())){
                remove(menuPermissionList,temp.get(i));
            }
        }

        //去除当前权限
        menuPermissionList.remove(permission);
    }


    @PostMapping("/{id:\\d+}/update")
    public String permissionUpdate(@PathVariable Integer id, Permission permission,Model model,RedirectAttributes redirectAttributes){
        try {
            rolePermissionService.findPermissionById(id);
            rolePermissionService.updatePermission(permission);
            redirectAttributes.addFlashAttribute("message", "修改成功");
            /*model.addAttribute("message", "修改成功...");*/
        }catch (ServiceException e){
            model.addAttribute("message", e.getMessage());
            return "error/custorm";
        }

        return "redirect:/manage/permission";
    }


    @GetMapping("/{id:\\d+}/del")
    @ResponseBody
    public ResponseBean delPermission(@PathVariable Integer id){
        try {
            rolePermissionService.delPermissionById(id);

        }catch (ServiceException e){
           return ResponseBean.error(e.getMessage());
        }

        return ResponseBean.success();
    }

    @GetMapping("/check/permissionName")
    @ResponseBody
    public boolean checkPermissionName(String permissionName){
        boolean result = rolePermissionService.findPermissionByName(permissionName);

        return result;
    }

    @GetMapping("/{id:\\d+}/check/permissionNameUpdate")
    @ResponseBody
    public boolean checkPermissionNameUpdate(@PathVariable Integer id ,String permissionName){
        boolean result = rolePermissionService.findPermissionByNameAndOwn(id ,permissionName);

        return result;
    }

    @GetMapping("/{id:\\d+}/check/permissionType")
    @ResponseBody
    public boolean checkPermissionType(@PathVariable Integer id,String permissionType){
        System.out.println("需要验证的permissionType: " + permissionType);
        System.out.println("需要验证的permissionId: " + id);

        boolean result = rolePermissionService.checkPermissionType(id, permissionType);

        return result;
    }

}
