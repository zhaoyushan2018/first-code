package com.xiaoshan.erp.service;

import com.xiaoshan.erp.entity.Employee;
import com.xiaoshan.erp.entity.FixOrder;
import com.xiaoshan.erp.entity.Order;
import com.xiaoshan.erp.exception.ServiceException;

import java.util.List;

/**
 * @author YushanZhao
 * @Date:2018/8/9
 */
public interface FixOrderService {

    /**
     * 将队列中的数据解析生成维修订单
     * @param json
     */
    void createFixOrder(String json);


    /**
     *  查找维修可用订单(查找 2已下发 和 3维修中的订单)
     * @return 返回维修列表
     */
    List<FixOrder> findFixOrderListLeftFixOrderParts();

    /**
     *  接受任务
     * @param id 维修任务里的订单号(fixOrder.orderId)
     * @param employee 接受任务的员工对象
     */
    void receiveTask(Integer id, Employee employee)throws ServiceException;

    /**
     *  根据id查找对象
     * @param id 订单Id
     * @return
     */
    FixOrder findFixOrderLeftFixOrderPartsById(Integer id);

    /**
     *  根据登录员工的id查找未完成的任务(3维修中)
     * @param employeeId 当前登录用户的Id
     * @return
     */
    FixOrder findUnDoneOrderByEmployeeId(Integer employeeId);

    /**
     *  根据登录员工的id查找已完成的任务列表(4维修完成)
     * @param employeeId 当前登录用户的Id
     * @return
     */
    List<FixOrder> findDoneOrderByEmployeeId(Integer employeeId);

    /**
     *  根据订单Id 把当前订单的状态改为(4.维修完成)
     * @param orderId 订单Id(fixOrder.orderId)
     */
    void doneFix(Integer orderId);

    /**
     *  查找所有需要质检的订单列表(4.维修完成 5.质检中)
     * @return
     */
    List<FixOrder> findCheckAll();


    /**
     *  根据当前登陆对象查找未质检完成的任务
     * @param employeeId
     * @return
     */
    FixOrder findUnDoneCheck(Integer employeeId);

    /**
     *  根据对象id(employeeId) 和 (fixOrder.state==6.结算中[就是质检已经完成]) 查找已完成的质检任务
     * @param employeeId 当前登陆员工Id
     * @return
     */
    List<FixOrder> findDoneCheck(Integer employeeId);


    /**
     *  修改状态
     * @param orderId 订单Id
     * @param employee 当前登陆对象
     * @param state 要修改的状态
     */
    void updateStateByOrderIdAndEmployeeAndState(Integer orderId, Employee employee, String state) throws ServiceException;

    /**
     *  完成质检任务
     *  订单状态 1.新订单 2.已下发 3.维修中 4.维修完成 5.质检中 6.结算中 7.完成
     *  完成订单不需要记录员工和订单关联关系, 不设置即可(接任务时已经更新过Id和Name)
     * @param orderId 订单Id
     * @param state 要修改的状态
     */
    void updateDoneStateByOrderIdAndState(Integer orderId, String state) throws ServiceException;
}
