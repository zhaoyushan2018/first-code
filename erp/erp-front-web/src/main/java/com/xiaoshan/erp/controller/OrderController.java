package com.xiaoshan.erp.controller;


import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.xiaoshan.erp.controller.exceptionHandler.NotFoundException;
import com.xiaoshan.erp.dto.ResponseBean;
import com.xiaoshan.erp.entity.*;
import com.xiaoshan.erp.exception.ServiceException;
import com.xiaoshan.erp.service.OrderService;
import com.xiaoshan.erp.service.PartsService;
import com.xiaoshan.erp.vo.OrderInfoVo;
import com.xiaoshan.erp.vo.OrderVo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author YushanZhao
 * @Date:2018/8/2
 */
@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private PartsService partsService;

    @GetMapping("/list")
    public String orderList(){

        return "manage/order/unlist";
    }

    @GetMapping("/new")
    public String orderNew(){

        return "manage/order/new";
    }

    @PostMapping("/new")
    @ResponseBody
    public ResponseBean orderNew(String json){
        System.out.println("接受的json数据: " + json);

        //将前端数据转化成对象
        Gson gson = new Gson();
        OrderVo orderVo = gson.fromJson(json, OrderVo.class);
        System.out.println("用一个自定义Vo类接受前端传来的的全部数据orderVo: " + orderVo);
        System.out.println("getPartsVoLists: --->" + orderVo.getPartsLists());


        //获得当前登录员工对象
        Subject subject = SecurityUtils.getSubject();
        Employee employee = (Employee) subject.getPrincipal();

        orderService.saveOrderWithOrderVoAndPartsVo(orderVo, employee.getId());

        return ResponseBean.success();
    }

    /**
     *
     * @return 全部服务类型
     */
    @GetMapping("/service/serviceTypes")
    @ResponseBody
    public ResponseBean serviceTypes(){
        List<ServiceType> serviceTypeList = orderService.findAllServiceType();

        return ResponseBean.success(serviceTypeList);
    }

    @GetMapping("/parts/partsTypes")
    @ResponseBody
    public ResponseBean allPartsTypes(){
        List<Type> typeList = orderService.findAllPartsType();

        return ResponseBean.success(typeList);
    }

    @GetMapping("/{id:\\d+}/partsTypeId")
    @ResponseBody
    public ResponseBean findAllPartsByPartsTypeId(@PathVariable Integer id){
        Type type = partsService.findPartsTypeByPartsTypeId(id);
        if (type == null){
            throw new NotFoundException();
        }

        List<Parts> partsList = partsService.findAllPartsByPartsTypeId(id);

        return ResponseBean.success(partsList);
    }

    /* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -  */

    @GetMapping("undone/unlist")
    public String undoneUnList(@RequestParam(name = "p", defaultValue = "1", required = false) Integer pageNo,
                               @RequestParam(required = false) String licenceNo,
                               @RequestParam(required = false) String tel,
                               @RequestParam(required = false) String startTime,
                               @RequestParam(required = false) String endTime,
                               Model model){

        //封装筛选的queryMap集合(封装筛选条件)
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("pageNo", pageNo);
        queryMap.put("licenceNo", licenceNo);
        queryMap.put("tel", tel);
        queryMap.put("startTime", startTime);
        queryMap.put("endTime", endTime);

        //查找状态条件为,完成以外的全部
        queryMap.put("exState", Order.ORDER_STATE_DONE);

        PageInfo<Order> page = orderService.findPageByParam(queryMap);


        model.addAttribute("page", page);
        model.addAttribute("type", "undone");
        return "manage/order/unlist";
    }


    @GetMapping("done/list")
    public String doneList(@RequestParam(name = "p", defaultValue = "1", required = false) Integer pageNo,
                               @RequestParam(required = false) String licenceNo,
                               @RequestParam(required = false) String tel,
                               @RequestParam(required = false) String startTime,
                               @RequestParam(required = false) String endTime,
                               Model model){

        //封装筛选的queryMap集合(封装筛选条件)
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("pageNo", pageNo);
        queryMap.put("licenceNo", licenceNo);
        queryMap.put("tel", tel);
        queryMap.put("startTime", startTime);
        queryMap.put("endTime", endTime);

        //查找状态条件为,完成以外的全部
        queryMap.put("state", Order.ORDER_STATE_DONE);

        PageInfo<Order> page = orderService.findPageByParam(queryMap);


        model.addAttribute("page", page);
        model.addAttribute("type", "done");
        return "manage/order/list";
    }

    /* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -  */


    @GetMapping("/{id:\\d+}/detail")
    public String detail(@PathVariable Integer id, Model model){
        //获得订单信息(根据订单Id(orderId))
        Order order = orderService.findOrderByOrderIdInCarAndCustomer(id);

        //获得订单服务类型信息
        Integer ServiceTypeId = order.getServiceTypeId();
        ServiceType serviceType = orderService.findServiceTypeById(ServiceTypeId);

        //获得配件订单列表
        Integer orderId = order.getId();
        List<Parts> partsList = partsService.findPartsByOrderPartsOrderId(orderId);

        model.addAttribute("order", order);
        model.addAttribute("serviceType", serviceType);
        model.addAttribute("partsList", partsList);

        return "/manage/order/detail";
    }


    @GetMapping("/{id:\\d+}/updateOrder")
    public String updateOrder(@PathVariable Integer id, Model model){
        model.addAttribute("orderId", id);

        return "manage/order/update";
    }

    @PostMapping("/{id:\\d+}/update")
    @ResponseBody
    public ResponseBean saveUpdate(String json){
        System.out.println("要保存的修改过的json数据为:json---> " + json);

        //将前端接受的json数据转化为对象
        Gson gson = new Gson();
        OrderVo orderVo = gson.fromJson(json, OrderVo.class);
        System.out.println("用对象接受的数据为: " + orderVo);

        try {
            orderService.saveUpdateOrder(orderVo);
        } catch (ServiceException e) {
            return ResponseBean.error(e.getMessage());
        }

        return ResponseBean.success();
    }



    @GetMapping("/{id:\\d+}/infoVo")
    @ResponseBody
    public ResponseBean infoVo(@PathVariable Integer id){

        //获得当前订单信息
        Order order = orderService.findOrderByOrderIdInCarAndCustomer(id);
        //获得订单服务类型信息
        ServiceType serviceType = orderService.findServiceTypeById(order.getServiceTypeId());
        //获得订单配件列表
        List<Parts> partsList = partsService.findPartsByOrderPartsOrderId(id);

        OrderInfoVo orderInfoVo = new OrderInfoVo();
        orderInfoVo.setOrder(order);
        orderInfoVo.setPartsList(partsList);
        orderInfoVo.setServiceType(serviceType);
        System.out.println("往前端回显要修改的数据orderInfoVo: " + orderInfoVo);

        return ResponseBean.success(orderInfoVo);
    }


    /**
     *  订单下发(改状态)
     * @return
     */
    @GetMapping("/{id:\\d+}/orderNextState")
    @ResponseBody
    public ResponseBean orderNextState(@PathVariable Integer id){
        try {
            orderService.orderNextState(id);
        } catch (ServiceException e) {
            return ResponseBean.error(e.getMessage());
        }

        return ResponseBean.success();
    }

    /**
     * 删除订单
     * @return
     */
    @GetMapping("/{id:\\d+}/delOrder")
    @ResponseBody
    public ResponseBean delOrderById(@PathVariable Integer id){

        try {
            orderService.delOrderById(id);
        } catch (ServiceException e) {
            return ResponseBean.error(e.getMessage());
        }

        return ResponseBean.success();
    }

}
