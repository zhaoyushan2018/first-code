package com.xiaoshan.erp.service.impl;

import com.google.gson.Gson;
import com.xiaoshan.erp.dto.FixOrderPartsDto;
import com.xiaoshan.erp.dto.OrderInfoDto;
import com.xiaoshan.erp.dto.OrderStateDto;
import com.xiaoshan.erp.entity.*;
import com.xiaoshan.erp.exception.ServiceException;
import com.xiaoshan.erp.mapper.FixOrderMapper;
import com.xiaoshan.erp.mapper.FixOrderPartsMapper;
import com.xiaoshan.erp.service.FixOrderService;
import com.xiaoshan.erp.util.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author YushanZhao
 * @Date:2018/8/9
 */
@Service
public class FixOrderServiceImpl implements FixOrderService {

    private Logger logger = LoggerFactory.getLogger(FixOrderServiceImpl.class);

    @Autowired
    private FixOrderMapper fixOrderMapper;

    @Autowired
    private FixOrderPartsMapper fixOrderPartsMapper;

    @Autowired
    private JmsTemplate jmsTemplate;

    /**
     * 将队列中的数据解析生成维修订单
     *
     * @param json
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void createFixOrder(String json) {
        //把json数据转换成对象
        OrderInfoDto orderInfoDto = new Gson().fromJson(json, OrderInfoDto.class);

        //解析orderInfoDto信息并入库
        //封装维修单
        FixOrder fixOrder = new FixOrder();
        fixOrder.setOrderId(orderInfoDto.getOrder().getId());
        fixOrder.setOrderType(orderInfoDto.getServiceType().getServiceName());
        fixOrder.setOrderTime(orderInfoDto.getOrder().getCreateTime());
        fixOrder.setState(orderInfoDto.getOrder().getState());
        fixOrder.setOrderMoney(orderInfoDto.getOrder().getOrderMoney());
        fixOrder.setOrderServiceHour(orderInfoDto.getServiceType().getServiceHour());
        //计算工时费{new BigDecimal()因为是钱数  小时数时字符串转换为int }
        fixOrder.setOrderServiceHourFee(new BigDecimal(Integer.parseInt(orderInfoDto.getServiceType().getServiceHour()) * Constant.DEFAULT_HOUR_FEE));
        //配件费用
        //(总钱数减去服务小时费,就是配件费用)
        fixOrder.setOrderPartsFee(fixOrder.getOrderMoney().subtract(fixOrder.getOrderServiceHourFee()));

        //封装车辆信息
        fixOrder.setCarType(orderInfoDto.getOrder().getCar().getCarType());
        fixOrder.setCarColor(orderInfoDto.getOrder().getCar().getColor());
        fixOrder.setCarLicence(orderInfoDto.getOrder().getCar().getLicenceNo());

        //封装客户信息
        fixOrder.setCustomerName(orderInfoDto.getOrder().getCustomer().getUserName());
        fixOrder.setCustomerTel(orderInfoDto.getOrder().getCustomer().getTel());

        //插入(封装的)数据
        fixOrderMapper.insertSelective(fixOrder);

        //配件列表入库
        for(Parts parts : orderInfoDto.getPartsList()){
            //封装维修订单配件
            FixOrderParts fixOrderParts = new FixOrderParts();
            fixOrderParts.setOrderId(orderInfoDto.getOrder().getId());
            fixOrderParts.setPartsId(parts.getId());
            fixOrderParts.setPartsNo(parts.getPartsNo());
            fixOrderParts.setPartsName(parts.getPartsName());
            fixOrderParts.setPartsNum(parts.getNum());

            //插入封装数据
            fixOrderPartsMapper.insertSelective(fixOrderParts);
        }

    }

    /**
     * 查找维修可用订单(查找 2已下发 和 3维修中的订单)
     *
     * @return 返回维修列表
     */
    @Override
    public List<FixOrder> findFixOrderListLeftFixOrderParts() {
        List<FixOrder> fixOrderList = fixOrderMapper.findFixOrderListLeftFixOrderParts();

        return fixOrderList;
    }

    /**
     * 接受任务
     *  订单状态 1.新订单 2.已下发 3.维修中 4.维修完成 5.质检中 6.结算中 7.完成
     * @param id       维修任务里的订单号(fixOrder.orderId)
     * @param employee 接受任务的员工对象
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void receiveTask(Integer id, Employee employee) throws ServiceException{
        //判断当前员工是否有未完成的任务
        FixOrderExample fixOrderExample = new FixOrderExample();
        fixOrderExample.createCriteria().andFixEmployeeIdEqualTo(employee.getId()).andStateEqualTo(Order.ORDER_STATE_FIXING);
        List<FixOrder> fixOrderList = fixOrderMapper.selectByExample(fixOrderExample);
        if(fixOrderList != null && fixOrderList.size() > 0){
            throw new ServiceException("要先完成当前任务,才能接受新任务哦...");
        }

        FixOrder fixOrder = fixOrderMapper.selectByPrimaryKey(id);
        if(fixOrder == null){
            throw new ServiceException("参数异常或者任务不存在...");
        }

        //状态改为维修中(3) 添加维修员工Id、名字  最后更新数据
        fixOrder.setState(Order.ORDER_STATE_FIXING);
        fixOrder.setFixEmployeeId(employee.getId());
        fixOrder.setFixEmployeeName(employee.getEmployeeName());
        fixOrderMapper.updateByPrimaryKey(fixOrder);

        //传输数据
        OrderStateDto orderStateDto = new OrderStateDto();
        orderStateDto.setOrderId(id);
        orderStateDto.setEmployeeId(employee.getId());
        orderStateDto.setState(Order.ORDER_STATE_FIXING);

        //发送数据到队列
        sendStateToMq(orderStateDto);

        //减少库存
        changePartsInvebtory(id, employee.getId());

    }



    /**
     *  维修工接取任务 消耗维修所用配件 减少库存
     *  发送消息到消息队列通知库存管理员减少对应配件的库存
     * @param orderId
     * @param employeeId
     */
    private void changePartsInvebtory(Integer orderId, Integer employeeId) {
        // 根据维修订单id(orderId),  查找对应的 所需全部的维修配件(List<FixOrderParts>)
        FixOrderPartsExample fixOrderPartsExample = new FixOrderPartsExample();
        fixOrderPartsExample.createCriteria().andOrderIdEqualTo(orderId);
        List<FixOrderParts> fixOrderPartsList = fixOrderPartsMapper.selectByExample(fixOrderPartsExample);

        // 封装 数据传输对象
        FixOrderPartsDto fixOrderPartsDto = new FixOrderPartsDto();
        fixOrderPartsDto.setEmployeeId(employeeId);
        fixOrderPartsDto.setFixOrderPartsList(fixOrderPartsList);

        //转换成json数据, 并发送
        String json = new Gson().toJson(fixOrderPartsDto);

        jmsTemplate.send("fixPartsNumQueue", new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(json);
            }
        });


    }


    /**
     *  发送订单状态到消息队列
     * @param orderStateDto 数据传输对象
     */
    private void sendStateToMq(OrderStateDto orderStateDto){
        //将对象转成json数据传输到mq中
        String json = new Gson().toJson(orderStateDto);
        jmsTemplate.send("stateQueue", new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(json);
            }
        });
    }


    /**
     * 根据id查找对象
     *
     * @param id 订单Id
     * @return
     */
    @Override
    public FixOrder findFixOrderLeftFixOrderPartsById(Integer id) {
        FixOrder fixOrder = fixOrderMapper.findFixOrderLeftFixOrderPartsById(id);
        return fixOrder;
    }

    /**
     * 根据登录员工的id查找未完成的任务(3维修中)
     *
     * @param employeeId 当前登录用户的Id
     * @return
     */
    @Override
    public FixOrder findUnDoneOrderByEmployeeId(Integer employeeId) {
        // 从员工Id(employeeId)和状态为维修中的(3) 两个条件查找, 因为只能接一个任务,完成了才能接下一个任务(所以List集合最多就一个)
        FixOrderExample fixOrderExample = new FixOrderExample();
        fixOrderExample.createCriteria().andFixEmployeeIdEqualTo(employeeId).andStateEqualTo(Order.ORDER_STATE_FIXING);
        List<FixOrder> fixOrderList = fixOrderMapper.selectByExample(fixOrderExample);

        //获取未完成的任务(根据id查找该任务的配件详情封装进去)
        FixOrder fixOrder = null;
        if(fixOrderList != null && fixOrderList.size() > 0){
            fixOrder = fixOrderList.get(0);
            //封装订单配件列表
            FixOrderPartsExample fixOrderPartsExample = new FixOrderPartsExample();
            fixOrderPartsExample.createCriteria().andOrderIdEqualTo(fixOrder.getOrderId());
            List<FixOrderParts> fixOrderPartsList = fixOrderPartsMapper.selectByExample(fixOrderPartsExample);

            fixOrder.setFixOrderPartsList(fixOrderPartsList);
        }

        return fixOrder;
    }

    /**
     * 根据登录员工的id查找已完成的任务列表(4维修完成)
     *
     * @param employeeId 当前登录用户的Id
     * @return
     */
    @Override
    public List<FixOrder> findDoneOrderByEmployeeId(Integer employeeId) {
        List<FixOrder> fixOrderList = fixOrderMapper.findDoneOrderByEmployeeIdAndState(employeeId, Order.ORDER_STATE_FIXED);

        return fixOrderList;
    }

    /**
     * 根据订单Id 把当前订单的状态改为(4.维修完成)
     *
     * @param orderId 订单Id(fixOrder.orderId)
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void doneFix(Integer orderId) {
        // 根据订单Id(orderId) 查找维修订单对象
        FixOrder fixOrder = fixOrderMapper.selectByPrimaryKey(orderId);
        if(fixOrder == null){
            throw new ServiceException("参数异常或者订单不存在...");
        }

        //更新状态(4.维修完成)
        fixOrder.setState(Order.ORDER_STATE_FIXED);
        fixOrderMapper.updateByPrimaryKeySelective(fixOrder);

        //完成订单不需要记录员工订单关联关系, 不设置即可
        OrderStateDto orderStateDto = new OrderStateDto();
        orderStateDto.setOrderId(orderId);
        orderStateDto.setState(Order.ORDER_STATE_FIXED);

        sendStateToMq(orderStateDto);
    }

    /*  质检员- - - - - - - - -  - - - - - - - -- - - - - - - - - - - -  - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -  - - - - - - - -- - - - - - - - - - - -  - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -  - - - - - - - - */


    /**
     * 查找所有需要质检的订单列表(4.维修完成 5.质检中)
     *
     * @return
     */
    @Override
    public List<FixOrder> findCheckAll() {
        //查找所有需要质检的订单(FixOrder.orderId == 4 || 5 )
        List<FixOrder> fixOrderList = fixOrderMapper.findCheckAll();

        return fixOrderList;
    }

    /**
     * 根据当前登陆对象查找未质检完成的任务
     *
     * @param employeeId
     * @return
     */
    @Override
    public FixOrder findUnDoneCheck(Integer employeeId) {
        //根据1.质检员id 和2.状态(5质检中) 两个条件查找质检员未完成的任务
        FixOrderExample fixOrderExample = new FixOrderExample();
        fixOrderExample.createCriteria().andCheckEmployeeIdEqualTo(employeeId).andStateEqualTo(Order.ORDER_STATE_CHECKING);
        List<FixOrder> fixOrderList = fixOrderMapper.selectByExample(fixOrderExample);

        //因为只能接取一个任务, 所以List集合里面只有一个或者为null
        FixOrder fixOrder = null;
        if(fixOrderList != null && fixOrderList.size() > 0 ){
            fixOrder = fixOrderList.get(0);
            // 封装配件列表到订单详情里面
            FixOrderPartsExample fixOrderPartsExample = new FixOrderPartsExample();
            fixOrderPartsExample.createCriteria().andOrderIdEqualTo(fixOrder.getOrderId());
            List<FixOrderParts> fixOrderPartsList = fixOrderPartsMapper.selectByExample(fixOrderPartsExample);
            fixOrder.setFixOrderPartsList(fixOrderPartsList);
        }

        return fixOrder;
    }

    /**
     * 根据对象id(employeeId) 和 (fixOrder.state==6.结算中[就是质检已经完成]) 查找已完成的质检任务
     *
     * @param employeeId 当前登陆员工Id
     * @return
     */
    @Override
    public List<FixOrder> findDoneCheck(Integer employeeId) {
        List<FixOrder> fixOrderList = fixOrderMapper.findDoneCheck(employeeId);

        return fixOrderList;
    }

    /**
     *  ------->>>> 接受任务
     * 修改状态
     *  订单状态 1.新订单 2.已下发 3.维修中 4.维修完成 5.质检中 6.结算中 7.完成
     * @param orderId  订单Id
     * @param employee 当前登陆对象
     * @param state    要修改的状态
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void updateStateByOrderIdAndEmployeeAndState(Integer orderId, Employee employee, String state) throws ServiceException{
        //判断当前员工是否有未完成的质检任务(根据员工Id和状态5)
        FixOrderExample fixOrderExample = new FixOrderExample();
        fixOrderExample.createCriteria().andCheckEmployeeIdEqualTo(employee.getId()).andStateEqualTo(Order.ORDER_STATE_CHECKING);
        List<FixOrder> fixOrderList = fixOrderMapper.selectByExample(fixOrderExample);
        if(fixOrderList != null && fixOrderList.size() > 0){
            throw new ServiceException("还有未完成的任务,您暂时不能接受新任务...");
        }

        FixOrder fixOrder = fixOrderMapper.selectByPrimaryKey(orderId);
        if(fixOrder == null){
            throw new ServiceException("参数异常或订单不存在...");
        }

        //数据库更新(添加质检员Id和质检员名字,更显状态)
        fixOrder.setState(state);
        fixOrder.setCheckEmployeeId(employee.getId());
        fixOrder.setCheckEmployeeName(employee.getEmployeeName());
        fixOrderMapper.updateByPrimaryKeySelective(fixOrder);

        //封装数据到消息队列(更改order里面的状态)
        OrderStateDto orderStateDto = new OrderStateDto();
        orderStateDto.setState(state);
        orderStateDto.setOrderId(orderId);
        orderStateDto.setEmployeeId(employee.getId());
        sendStateToMq(orderStateDto);

    }

    /**
     * 完成质检任务
     * 订单状态 1.新订单 2.已下发 3.维修中 4.维修完成 5.质检中 6.结算中 7.完成
     * 完成订单不需要记录员工和订单关联关系, 不设置即可(接任务时已经更新过Id和Name)
     *
     * @param orderId 订单Id
     * @param state   要修改的状态
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void updateDoneStateByOrderIdAndState(Integer orderId, String state) throws ServiceException{
        // 根据订单Id查找对象, 不存在抛异常 存在则修改状态
        FixOrder fixOrder = fixOrderMapper.selectByPrimaryKey(orderId);
        if(fixOrder == null){
            throw new ServiceException("参数异常或者该订单不存在...");
        }

        fixOrder.setState(state);
        fixOrderMapper.updateByPrimaryKeySelective(fixOrder);

        // 同步数据  修改完维修数据库 修改Order里面数据
        OrderStateDto orderStateDto = new OrderStateDto();
        orderStateDto.setState(state);
        orderStateDto.setOrderId(orderId);

        //发送
        sendStateToMq(orderStateDto);
    }















}
