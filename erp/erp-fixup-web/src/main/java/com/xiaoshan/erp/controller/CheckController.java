package com.xiaoshan.erp.controller;

import com.xiaoshan.erp.dto.ResponseBean;
import com.xiaoshan.erp.entity.Employee;
import com.xiaoshan.erp.entity.FixOrder;
import com.xiaoshan.erp.entity.Order;
import com.xiaoshan.erp.exception.ServiceException;
import com.xiaoshan.erp.service.FixOrderService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author YushanZhao
 * @Date:2018/8/10
 */
@Controller
@RequestMapping("/check")
public class CheckController {

    @Autowired
    private FixOrderService fixOrderService;

    @GetMapping("/checkList")
    public String checkList(Model model){
        List<FixOrder> fixOrderList = fixOrderService.findCheckAll();
        model.addAttribute("fixOrderList", fixOrderList);

        System.out.println("质检任务清单为---> " + fixOrderList);

        return "manage/check/checkList";
    }

    @GetMapping("/checkService")
    public String checkService(){

        return "manage/check/checkService";
    }


    /**
     * 已接未完成的任务
     * @return
     */
    @GetMapping("/unDoneCheck")
    public String unDoneCheck(Model model){
        // 获取当前登陆对象, 根据对象Id(checkEmployeeId)查找
        Subject subject = SecurityUtils.getSubject();
        Employee employee = (Employee) subject.getPrincipal();

        // 只有完成当前任务才能接取下一个任务,`所以未完成任务只能有一个
        // 根据当前登陆对象查找未质检完成的任务
        FixOrder fixOrder = fixOrderService.findUnDoneCheck(employee.getId());
        model.addAttribute("fixOrder", fixOrder);
        model.addAttribute("currEmployeeId", employee.getId());
        model.addAttribute("type", "undone");

        return "manage/check/checkService";
    }

    /**
     *  已完成质检任务列表
     * @return
     */
    @GetMapping("/donelistCheck")
    public String donelistCheck(Model model){
        // 获得当前登陆对象  根据对象id(employeeId)查找已完成的质检任务(fixOrder.state==6.结算中[就是质检已经完成])
        Subject subject = SecurityUtils.getSubject();
        Employee employee = (Employee) subject.getPrincipal();

        List<FixOrder> fixOrderList = fixOrderService.findDoneCheck(employee.getId());
        model.addAttribute("fixOrderList", fixOrderList);
        model.addAttribute("currEmployeeId", employee.getId());
        model.addAttribute("type", "done");

        return "manage/check/checkDoneListService";
    }

    /**
     *  领取质检任务 (4.维修完成 ->->-> 5.质检中)
     * @return
     */
    @GetMapping("/{id:\\d+}/receive")
    @ResponseBody
    public ResponseBean receiveCheck(@PathVariable Integer id){
        Subject subject = SecurityUtils.getSubject();
        Employee employee = (Employee) subject.getPrincipal();

        try {
            //领取任务,修改状态
            fixOrderService.updateStateByOrderIdAndEmployeeAndState(id, employee, Order.ORDER_STATE_CHECKING);
        } catch (ServiceException e) {
            return ResponseBean.error(e.getMessage());
        }

        return  ResponseBean.success();
    }

    /**
     *  接受完任务跳转详情页面
     * @return
     */
    @GetMapping("/{id:\\d+}/detail")
    public String detail(@PathVariable Integer id, Model model){
        //获得当前登录员工对象
        Employee employee = getThisNowEmployee();

        // 根据订单id获得对象
        FixOrder fixOrder = fixOrderService.findFixOrderLeftFixOrderPartsById(id);
        model.addAttribute("fixOrder", fixOrder);
        model.addAttribute("currEmployeeId", employee.getId());
        model.addAttribute("type", "undone");

        return "manage/check/checkService";
    }

    /**
     *  完成质检任务
     *  订单状态 1.新订单 2.已下发 3.维修中 4.维修完成 5.质检中 6.结算中 7.完成
     * @param id
     * @return
     */
    @GetMapping("/{id:\\d+}/doneCheck")
    @ResponseBody
    public ResponseBean doneCheck(@PathVariable Integer id){
        try {
            // 完成订单不需要记录员工和订单关联关系, 不设置即可(接任务时已经更新过Id和Name)
            fixOrderService.updateDoneStateByOrderIdAndState(id, Order.ORDER_STATE_SETTLEMENT);
        } catch (ServiceException e) {
            return ResponseBean.error(e.getMessage());
        }

        return ResponseBean.success();
    }

    /* - - - - - - - - - - - -  - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -  - - - - - - - -  */
    /**
     *  获得当前登录(员工)对象
     * @return
     */
    private Employee getThisNowEmployee(){
        Subject subject = SecurityUtils.getSubject();
        Employee employee = (Employee) subject.getPrincipal();
        return employee;
    }

}
