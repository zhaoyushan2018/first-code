package com.xiaoshan.erp.controller;

import com.xiaoshan.erp.controller.exceptionHandler.NotFoundException;
import com.xiaoshan.erp.dto.ResponseBean;
import com.xiaoshan.erp.entity.Permission;
import com.xiaoshan.erp.entity.Role;
import com.xiaoshan.erp.exception.ServiceException;
import com.xiaoshan.erp.service.RolePermissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

/**
 * @author YushanZhao
 * @Date:2018/7/27
 */
@Controller
@RequestMapping("/manage/role")
public class RoleController {

    Logger logger = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private RolePermissionService rolePermissionService;

    @GetMapping
    public String homeRole(Model model){
        List<Role> roleList = rolePermissionService.findListRoleLiftPermission();
        model.addAttribute("roleList", roleList);

        return "manage/role/home";
    }

    @GetMapping("/new")
    public String roleNew(Model model){
        List<Permission> permissionList = rolePermissionService.findAllPermission();
        model.addAttribute("permissionList", permissionList);

        return "manage/role/new";
    }

    @PostMapping("/new")
    public String roleNew(Role role, Integer[] permissionId, RedirectAttributes redirectAttributes){
        /*System.out.println(role.toString());
        System.out.println(role.getRoleName());
        System.out.println(role.getRoleCode());
        for (Integer id : permissionId){
            System.out.println(id);
        }*/
        rolePermissionService.saveRole(role, permissionId);
        redirectAttributes.addFlashAttribute("message", "角色增加成功...");

        return "redirect:/manage/role";
    }

    @GetMapping("/{id:\\d+}/update")
    public String roleUpdate(@PathVariable Integer id, Model model){
        Role role = rolePermissionService.findRoleLeftPernissionById(id);
        if(role == null){
            throw new NotFoundException();
        }

        /*logger.debug("当前id权限: " + role);
        logger.debug("当前角色的权限数量为: " + role.getPermissionList().size());*/

        //当前id的permissionList(权限列表)
        List<Permission> rolePermissionList = role.getPermissionList();

        //获得有序的map集合 value为:当前id有权限为tue, 没有的为false
        Map<Permission, Boolean> permissionBooleanMap = rolePermissionService.permissionBooleanMap(rolePermissionList);

        model.addAttribute("permissionBooleanMap", permissionBooleanMap);
        model.addAttribute("role", role);

        return "manage/role/update";
    }

    @PostMapping("/{id:\\d+}/update")
    public String roleUpdate(Role role, Integer[] permissionId){
        rolePermissionService.updateRole(role, permissionId);

        /*System.out.println("对象: " + role);
        for (Integer id : permissionId){
            System.out.println("选中权限id: " + id);
        }*/
        return "redirect:/manage/role";
    }


    /**
     * 验证角色名字是否重复
     * @return 返回 重复(不可用false) 不重复(可用true)
     */
    @GetMapping("/check/roleName")
    @ResponseBody
    public Boolean checkRoleName(String roleName){
        Boolean result = rolePermissionService.checkRoleName(roleName);

        return result;
    }

    /**
     * 验证修改的角色名字是否(除自己外的角色名)重复
     * @return 返回 重复(不可用false) 不重复(可用true)
     */
    @GetMapping("/check/{id:\\d+}/updateRoleName")
    @ResponseBody
    public Boolean checkUpdateRoleName(@PathVariable Integer id,String roleName){
        /*System.out.println("要修改的当前的id: " + id);
        System.out.println("要修改的当前的roleName: " + roleName);*/

        Boolean result = rolePermissionService.checkUpdateRoleName(id,roleName);

        return result;
    }

    @GetMapping("/{id:\\d+}/delRole")
    @ResponseBody
    public ResponseBean delRoleById(@PathVariable Integer id){
        try {
            rolePermissionService.delRoleById(id);
            return ResponseBean.success();

        }catch (ServiceException e){
            return ResponseBean.error(e.getMessage());
        }
    }


}
