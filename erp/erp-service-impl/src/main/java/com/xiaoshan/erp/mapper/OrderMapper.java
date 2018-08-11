package com.xiaoshan.erp.mapper;

import com.xiaoshan.erp.entity.Order;
import com.xiaoshan.erp.entity.OrderExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface OrderMapper {
    long countByExample(OrderExample example);

    int deleteByExample(OrderExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Order record);

    int insertSelective(Order record);

    List<Order> selectByExample(OrderExample example);

    Order selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Order record, @Param("example") OrderExample example);

    int updateByExample(@Param("record") Order record, @Param("example") OrderExample example);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

    List<Order> findUnDonePageByParamleftCarAndCustomer(Map<String,Object> queryMap);

    /**
     * 查找订单(订单里面有汽车、客户的全部信息)
     * @param orderId
     * @return
     */
    Order findOrderByOrderIdInCarAndCustomer(Integer orderId);
}