package com.xiaoshan.erp.controller;

import com.xiaoshan.erp.dto.ResponseBean;
import com.xiaoshan.erp.entity.Employee;
import com.xiaoshan.erp.entity.Role;
import com.xiaoshan.erp.exception.ServiceException;
import com.xiaoshan.erp.service.EmployeeService;
import com.xiaoshan.erp.service.RolePermissionService;
import com.xiaoshan.erp.service.RoleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * @author YushanZhao
 * @Date:2018/7/25
 */
@Controller
@RequestMapping("/manage/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private RolePermissionService rolePermissionService;

    @Autowired
    private RoleService roleService;

    @GetMapping
    public String employeeHome(Model model){
        List<Employee> employeeList = employeeService.findAllEmployee();
        if(employeeList != null && employeeList.size() > 0){
            for(Employee employee : employeeList){
                System.out.println("employee: " + employee);
            }
        } else {
            System.out.println("-------------------------------------------------------------------------------------");
        }


        model.addAttribute("employeeList", employeeList);

        return "manage/employee/home";
    }

    @GetMapping("/new")
    @RequiresPermissions("employee:add")
    public String newEmployee(Model model){
        List<Role> roleList = roleService.findAllRoleList();
        model.addAttribute("roleList", roleList);

        return "manage/employee/new";
    }

    @PostMapping("/new")
    @RequiresPermissions("employee:add")
    public String newEmployee(Employee employee, Integer[] roleIds){
        //保存员工(employee)对象 并保存员工和角色关联关系
        employeeService.saveEmployee(employee, roleIds);

        return "redirect:/manage/employee";
    }

    @GetMapping("/check/employeeTel")
    @ResponseBody
    public boolean checkEmployeeTel(String employeeTel) {
        List<Employee> employeeList = employeeService.findEmployeeByTell(employeeTel);

        System.out.println("要查询的登陆电话为: " + employeeTel);

        if (employeeList != null && employeeList.size() > 0) {
            return false;
        }
        return true;
    }

    @GetMapping("/{id:\\d+}/stopEmployee")
    @ResponseBody
    public ResponseBean stopEmployee(@PathVariable Integer id){
        System.out.println("要禁用的id为： " + id);

        try {
            employeeService.stopStateEmployeeById(id);
            return ResponseBean.success();

        }catch (ServiceException e){
            return ResponseBean.error(e.getMessage());
        }

    }


    @GetMapping("/{id:\\d}/delEmployee")
    @ResponseBody
    public ResponseBean delEmployee(@PathVariable Integer id){
        System.out.println("要删除的id为: " + id + "---------------------------");

        try {
            employeeService.delEmployeeById(id);
        }catch (ServiceException e){
            return ResponseBean.error(e.getMessage());
        }
        return ResponseBean.success();
    }

    @GetMapping("/{id:\\d+}/updateEmployee")
    public String updateEmployee(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes){
        System.out.println("要修改的id为: " + id);
        try {
            Employee employee = employeeService.findEmployeeById(id);
            model.addAttribute("employee", employee);

            //全部角色列表
            List<Role> allRoleList = roleService.findAllRoleList();
            model.addAttribute("allRoleList", allRoleList);

            //获得当前员角色的列表
            List<Role> nowRoleList = rolePermissionService.findRoleListByEmployeeId(id);
            model.addAttribute("nowRoleList", nowRoleList);

            return "manage/employee/update";
        }catch (ServiceException e){

            redirectAttributes.addFlashAttribute("message",e.getMessage());
            return "redirect:/manage/employee";
        }


    }

}
