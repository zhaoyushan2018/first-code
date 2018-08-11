package com.xiaoshan.erp.service;

import com.github.pagehelper.PageInfo;
import com.xiaoshan.erp.entity.Order;
import com.xiaoshan.erp.entity.ServiceType;
import com.xiaoshan.erp.entity.Type;
import com.xiaoshan.erp.exception.ServiceException;
import com.xiaoshan.erp.vo.OrderVo;

import java.util.List;
import java.util.Map;

/**
 * @author YushanZhao
 * @Date:2018/8/4
 */
public interface OrderService {

    /**
     *  查找全部服务类型并返回
     * @return 全部服务类型集合
     */
    List<ServiceType> findAllServiceType();

    /**
     *  查找所有配件类型集合
     * @return
     */
    List<Type> findAllPartsType();

    /**
     *  保存保养维修单
     *  orderVo(carId车辆Id,serviceTypeId服务类型Id,fee总计钱数,partsLists需要配件集合[配件Id,数量])
     * @param orderVo(carId车辆Id,serviceTypeId服务类型Id,fee总计钱数,partsLists需要配件集合[配件Id,数量])
     * @param employeeId 当前登录员工的id
     */
    void saveOrderWithOrderVoAndPartsVo(OrderVo orderVo, Integer employeeId);

    /**
     *  通过参数查询订单
     * @param queryMap 查询条件
     * @return
     */
    PageInfo<Order> findPageByParam(Map<String,Object> queryMap);

    /**
     * 根据订单Id查找对象
     * @param orderId orderId订单id
     * @return
     */
    Order findOrderByOrderIdInCarAndCustomer(Integer orderId);

    /**
     *  根据id查找对象
     * @param serviceTypeId 服务类型Id
     * @return
     */
    ServiceType findServiceTypeById(Integer serviceTypeId);

    /**
     * 订单下发
     * @param id 订单id
     */
    void orderNextState (Integer id) throws ServiceException;

    /**
     *  保存修改后的订单
     * @param orderVo
     */
    void saveUpdateOrder(OrderVo orderVo) throws ServiceException;

    /**
     *  根据订单Id删除
     * @param id orderId
     */
    void delOrderById(Integer id) throws ServiceException;

    /**
     * 解析json数据改变订单状态
     *  消费者(从队列[修理系统传来]获取的数据)
     * @param json 数据
     */
    void editOrderState(String json);
}
