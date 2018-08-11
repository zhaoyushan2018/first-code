package com.xiaoshan.erp.controller;

import com.xiaoshan.erp.dto.ResponseBean;
import com.xiaoshan.erp.entity.Employee;
import com.xiaoshan.erp.entity.FixOrder;
import com.xiaoshan.erp.entity.FixOrderParts;
import com.xiaoshan.erp.entity.Order;
import com.xiaoshan.erp.exception.ServiceException;
import com.xiaoshan.erp.service.FixOrderService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author YushanZhao
 * @Date:2018/8/8
 */
@Controller
@RequestMapping("/fix")
public class FixController {

    @Autowired
    private FixOrderService fixOrderService;

    @GetMapping("/fixList")
    public String fixList(Model model){
        // 查找维修可用订单(查找 2已下发 和 3维修中的订单)
        List<FixOrder> fixOrderList = fixOrderService.findFixOrderListLeftFixOrderParts();
        model.addAttribute("fixOrderList", fixOrderList);

        System.out.println("查找出来的维修可用订单fixOrderList: ---> " + fixOrderList);
        for(FixOrder fixOrder : fixOrderList){
            System.out.println(fixOrder);
            for(FixOrderParts fixOrderParts : fixOrder.getFixOrderPartsList()){
                System.out.println("------->" + fixOrderParts);
            }
        }

        return "manage/fix/fixList";
    }

/*    @GetMapping("/fixService")
    public String fixService(){
        //获取当前登录员工对象

        return "manage/fix/fixService";
    }*/

    /* - - - - - - - - - - - -  - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -  - - - - - - - -  */

    /**
     *  接受任务
     * @return
     */
    @GetMapping("/{id:\\d+}/receive")
    @ResponseBody
    public ResponseBean receiveTask(@PathVariable Integer id){
        System.out.println("接受任务的Id : " + id);
        //获取当前登陆员工对象
        Subject subject = SecurityUtils.getSubject();
        Employee employee = (Employee) subject.getPrincipal();
        System.out.println("当前登陆员工对象为: " + employee);

        try {
            fixOrderService.receiveTask(id, employee);
        } catch (ServiceException e) {
            return ResponseBean.error(e.getMessage());
        }

        return ResponseBean.success();
    }

    /* 任务详情列表 - - - - - - - - - - - -  - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -  - - - - - - - -  */

    @GetMapping("/{id:\\d+}/detail")
    public String orderDetail(@PathVariable Integer id, Model model){
        // 根据订单Id 获得任务详情
        FixOrder fixOrder = fixOrderService.findFixOrderLeftFixOrderPartsById(id);
        System.out.println("任务详情列表为:--->" + fixOrder);

        //获取当前登陆员工对象
        Subject subject = SecurityUtils.getSubject();
        Employee employee = (Employee) subject.getPrincipal();

        //把数据传到前端, 详情列表
        model.addAttribute("fixOrder",fixOrder);
        model.addAttribute("currEmployeeId",employee.getId());
        model.addAttribute("type", "undone");

        return "manage/fix/fixService";
    }


    /*  - - - - - - - - - - - -  - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -  - - - - - - - - */

    /**
     *  未完成任务
     * @param model
     * @return
     */
    @GetMapping("/unDoneOrder")
    public String unDoneOrder(Model model){
        // 获得当前登陆对象  根据对象id(employeeId)查找未完成的任务(fixOrder.state==3[维修中])
        Subject subject = SecurityUtils.getSubject();
        Employee employee = (Employee) subject.getPrincipal();

        FixOrder fixOrder = fixOrderService.findUnDoneOrderByEmployeeId(employee.getId());
        model.addAttribute("fixOrder",fixOrder);
        model.addAttribute("currEmployeeId",employee.getId());
        model.addAttribute("type", "undone");

        return "manage/fix/fixService";
    }


    @GetMapping("/donelistOrder")
    public String donelistOrder(Model model){
        // 获得当前登陆对象  根据对象id(employeeId)查找已完成的任务(fixOrder.state==4[维修完成])
        Subject subject = SecurityUtils.getSubject();
        Employee employee = (Employee) subject.getPrincipal();

        List<FixOrder> fixOrderList = fixOrderService.findDoneOrderByEmployeeId(employee.getId());
        model.addAttribute("fixOrderList", fixOrderList);
        model.addAttribute("type", "done");

        System.out.println("当前登录员工为" + employee.getEmployeeName() );
        System.out.println("当前员工已经完成的任务--->" + fixOrderList);

        return "manage/fix/doneFixService";
    }

    /*  - - - - - - - - - - - -  - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -  - - - - - - - - */

    /**
     * @param id  订单Id(fixOrder.orderId)
     * @return
     */
    @GetMapping("/{id:\\d+}/doneFix")
    public String doneFix(@PathVariable Integer id){
        // 根据订单Id 把当前订单的状态改为(4.维修完成)
        fixOrderService.doneFix(id);

        return "redirect:/fix/donelistOrder";
    }




}
