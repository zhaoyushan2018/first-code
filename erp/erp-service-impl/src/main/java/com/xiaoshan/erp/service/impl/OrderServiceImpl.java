package com.xiaoshan.erp.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.xiaoshan.erp.dto.OrderStateDto;
import com.xiaoshan.erp.entity.*;
import com.xiaoshan.erp.exception.ServiceException;
import com.xiaoshan.erp.mapper.*;
import com.xiaoshan.erp.service.OrderService;
import com.xiaoshan.erp.util.Constant;
import com.xiaoshan.erp.vo.OrderInfoVo;
import com.xiaoshan.erp.vo.OrderVo;
import com.xiaoshan.erp.vo.PartsVo;
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
import java.util.List;
import java.util.Map;

/**
 * @author YushanZhao
 * @Date:2018/8/4
 */
@Service
public class OrderServiceImpl implements OrderService {

    Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderPartsMapper orderPartsMapper;

    @Autowired
    private OrderEmployeeMapper orderEmployeeMapper;

    @Autowired
    private ServiceTypeMapper serviceTypeMapper;

    @Autowired
    private TypeMapper typeMapper;

    @Autowired
    private PartsMapper partsMapper;

    @Autowired
    private JmsTemplate jmsTemplate;

    /**
     * 查找全部服务类型并返回
     *
     * @return 全部服务类型集合
     */
    @Override
    public List<ServiceType> findAllServiceType() {
        ServiceTypeExample serviceTypeExample = new ServiceTypeExample();
        List<ServiceType> serviceTypeList = serviceTypeMapper.selectByExample(serviceTypeExample);

        return serviceTypeList;
    }

    /**
     * 查找所有配件类型集合
     *
     * @return
     */
    @Override
    public List<Type> findAllPartsType() {
        TypeExample typeExample = new TypeExample();
        List<Type> typeList = typeMapper.selectByExample(typeExample);

        return typeList;
    }

    /**
     * 保存保养维修单
     * orderVo(carId车辆Id,serviceTypeId服务类型Id,fee总计钱数,partsLists需要配件集合[配件Id,数量])
     * @param orderVo 前端订单信息
     * @param employeeId 操作员工的id
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void saveOrderWithOrderVoAndPartsVo(OrderVo orderVo, Integer employeeId) {
        // 创建order对象, 并赋值(从参数里面获取).
        Order order = new Order();
        order.setOrderMoney(orderVo.getFee());
        order.setState(Order.ORDER_STATE_NEW);
        order.setCarId(orderVo.getCarId());
        order.setServiceTypeId(orderVo.getServiceTypeId());

        //保存对象 获取自增id
        orderMapper.insertSelective(order);
        Integer orderId = order.getId();
        System.out.println("获取的自增id为: " + orderId);

        //保存服务所需配件及数量 和服务之间的关联关系. (orderVo.getPartsVoList()是一个集合,所以需要迭代)
        for(PartsVo partsVo : orderVo.getPartsLists()){
            OrderParts orderParts = new OrderParts();
            orderParts.setOrderId(orderId);
            orderParts.setPartsId(partsVo.getId());
            orderParts.setNum(partsVo.getNum());

            orderPartsMapper.insertSelective(orderParts);
        }

        // 新增员工和订单的关联关系
        OrderEmployee orderEmployee = new OrderEmployee();
        orderEmployee.setEmployeeId(employeeId);
        orderEmployee.setOrderId(orderId);
        orderEmployeeMapper.insertSelective(orderEmployee);
    }

    /**
     * 通过参数查询订单
     *
     * @param queryMap 查询条件
     * @return
     */
    @Override
    public PageInfo<Order> findPageByParam(Map<String, Object> queryMap) {
        //从Map集合用Key获得的是Object,先转为String 再转为Integer 获得页码 (开始页,显示几条数据)
        PageHelper.startPage(Integer.parseInt(String.valueOf(queryMap.get("pageNo"))), Constant.DEFAULT_PAGE_SIZE);
        List<Order> orderList = orderMapper.findUnDonePageByParamleftCarAndCustomer(queryMap);
        System.out.println("查出来的未完成数据orderList: " + orderList);

        PageInfo<Order> orderPageInfo = new PageInfo<>(orderList);
        return orderPageInfo;
    }

    /**
     * 根据订单Id查找对象
     *
     * @param orderId orderId订单id
     * @return
     */
    @Override
    public Order findOrderByOrderIdInCarAndCustomer(Integer orderId) {
        Order order = orderMapper.findOrderByOrderIdInCarAndCustomer(orderId);

        return order;
    }

    /**
     * 根据id查找对象
     *
     * @param serviceTypeId 服务类型Id
     * @return
     */
    @Override
    public ServiceType findServiceTypeById(Integer serviceTypeId) {
        ServiceType serviceType = serviceTypeMapper.selectByPrimaryKey(serviceTypeId);

        return serviceType;
    }

    /**
     * 订单下发
     *
     * @param id 订单id
     */
    @Override
    //不管什么异常都回滚
    @Transactional(rollbackFor = RuntimeException.class)
    public void orderNextState(Integer id) throws ServiceException{
        Order order = orderMapper.selectByPrimaryKey(id);
        if(order == null){
            throw new ServiceException("参数异常或订单不存在...");
        }

        if(!order.getState().equals(Order.ORDER_STATE_NEW)){
            throw new ServiceException("该订单已经生成并且下发,操作失败...");
        }

        //设置订单状态为已经下发 2
        order.setState(Order.ORDER_STATE_TRANS);
        orderMapper.updateByPrimaryKeySelective(order);

        //订单下发后将订单信息发送到消息队列
        sendOrderInfoMq(id);
    }

    /**
     * 发送订单详细信息到队列
     * @param id
     */
    private void sendOrderInfoMq(Integer id){
        //1.获得订单详细信息
        Order order = orderMapper.findOrderByOrderIdInCarAndCustomer(id);

        //2.获得订单服务类型信息
        ServiceType serviceType = serviceTypeMapper.selectByPrimaryKey(order.getServiceTypeId());

        //3.获得订单配件列表
        List<Parts> partsList = partsMapper.findPartsListByOrderPartsOrderId(id);

        //4.封装 数据传输对象
        OrderInfoVo orderInfoVo = new OrderInfoVo();
        orderInfoVo.setOrder(order);
        orderInfoVo.setServiceType(serviceType);
        orderInfoVo.setPartsList(partsList);

        //5.转成json数据发送到队列mq
        String json = new Gson().toJson(orderInfoVo);

        jmsTemplate.send(new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(json);
            }
        });

    }

    /**
     * 保存修改后的订单
     *
     * @param orderVo
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void saveUpdateOrder(OrderVo orderVo) throws ServiceException{
        //查找order对象
        Order order = orderMapper.selectByPrimaryKey(orderVo.getId());
        if(order == null ){
            throw new ServiceException("参数异常或订单不存在...");
        }

        //只有状态为1(新订单)才能修改
        if(!order.getState().equals(Order.ORDER_STATE_NEW)){
            throw new ServiceException("订单已经下发并生成订单,不能修改...");
        }

        //修改订单(总钱数. 服务类型. 汽车)
        order.setOrderMoney(orderVo.getFee());
        order.setServiceTypeId(orderVo.getServiceTypeId());
        order.setCarId(orderVo.getCarId());
        orderMapper.updateByPrimaryKeySelective(order);

        //删除原有的关联关系(订单配件)
        OrderPartsExample orderPartsExample = new OrderPartsExample();
        orderPartsExample.createCriteria().andOrderIdEqualTo(orderVo.getId());
        orderPartsMapper.deleteByExample(orderPartsExample);

        //新建关联关系(订单配件)
        List<PartsVo> partsVoList = orderVo.getPartsLists();
        for(PartsVo partsVo : partsVoList){
            OrderParts orderParts = new OrderParts();
            orderParts.setOrderId(orderVo.getId());
            orderParts.setPartsId(partsVo.getId());
            orderParts.setNum(partsVo.getNum());
            orderPartsMapper.insertSelective(orderParts);
            logger.info("新增订单配件关联关系---------->: " + orderParts);
        }
    }

    /**
     * 根据订单Id删除
     *
     * @param id orderId
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void delOrderById(Integer id) throws ServiceException{
        Order order = orderMapper.selectByPrimaryKey(id);
        if(order == null){
            throw new ServiceException("参数异常或订单不存在...");
        }

        //删除订单和员工的关联关系
        OrderEmployeeExample orderEmployeeExample = new OrderEmployeeExample();
        orderEmployeeExample.createCriteria().andOrderIdEqualTo(id);
        orderEmployeeMapper.deleteByExample(orderEmployeeExample);

        //删除订单和配件的关联关系
        OrderPartsExample orderPartsExample = new OrderPartsExample();
        orderPartsExample.createCriteria().andOrderIdEqualTo(id);
        orderPartsMapper.deleteByExample(orderPartsExample);

        //删除订单信息
        orderMapper.deleteByPrimaryKey(id);

    }

    /**
     *  解析json数据改变订单状态
     * 消费者(从队列[修理系统传来]获取的数据)
     *
     * @param json 数据
     * @Transactional(rollbackFor = RuntimeException.class) 不管什么异常都回滚
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void editOrderState(String json) {
        OrderStateDto orderStateDto = new Gson().fromJson(json, OrderStateDto.class);
        Order order = orderMapper.selectByPrimaryKey(orderStateDto.getOrderId());

        if(order == null){
            logger.error("orderId : {} ---> 订单不存在",orderStateDto.getOrderId());
        }

        //订单存在的话, 改变状态
        order.setState(orderStateDto.getState());
        orderMapper.updateByPrimaryKeySelective(order);

        //新增订单操作员工
        //如果员工的id为null(employeeId=null) 则代表订单和员工的关联关系不需要新增
        if(orderStateDto.getEmployeeId() != null){
            OrderEmployee orderEmployee = new OrderEmployee();
            orderEmployee.setOrderId(orderStateDto.getOrderId());
            orderEmployee.setEmployeeId(orderStateDto.getEmployeeId());
            orderEmployeeMapper.insertSelective(orderEmployee);
        }

    }


}
