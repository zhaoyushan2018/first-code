package com.xiaoshan.erp.mapper;

import com.xiaoshan.erp.entity.FixOrder;
import com.xiaoshan.erp.entity.FixOrderExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FixOrderMapper {
    long countByExample(FixOrderExample example);

    int deleteByExample(FixOrderExample example);

    int deleteByPrimaryKey(Integer orderId);

    int insert(FixOrder record);

    int insertSelective(FixOrder record);

    List<FixOrder> selectByExample(FixOrderExample example);

    FixOrder selectByPrimaryKey(Integer orderId);

    int updateByExampleSelective(@Param("record") FixOrder record, @Param("example") FixOrderExample example);

    int updateByExample(@Param("record") FixOrder record, @Param("example") FixOrderExample example);

    int updateByPrimaryKeySelective(FixOrder record);

    int updateByPrimaryKey(FixOrder record);

    /**
     * 查找维修可用订单(查找 2已下发 和 3维修中的订单)
     * @return 返回维修可用的列表
     */
    List<FixOrder> findFixOrderListLeftFixOrderParts();

    /**
     *   根据dingdanId查找 订单及订单所需配件List列表
     * @param id 订单Id
     * @return
     */
    FixOrder findFixOrderLeftFixOrderPartsById(Integer id);

    /**
     *  根据登录员工的id查找已完成的任务列表(4维修完成)
     * @param employeeId 当前登录用户的Id
     * @param doneState  4维修完成
     * @return
     */
    List<FixOrder> findDoneOrderByEmployeeIdAndState(Integer employeeId, String doneState);

    /**
     * 查找所有需要质检的订单(FixOrder.orderId == 4 || 5)
     * @return
     */
    List<FixOrder> findCheckAll();

    /**
     *  查找已完成的质检任务
     * @param employeeId 质检员Id
     * @return
     */
    List<FixOrder> findDoneCheck(Integer employeeId);
}