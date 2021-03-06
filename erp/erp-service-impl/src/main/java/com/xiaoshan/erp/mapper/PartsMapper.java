package com.xiaoshan.erp.mapper;

import com.xiaoshan.erp.entity.Parts;
import com.xiaoshan.erp.entity.PartsExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface PartsMapper {
    long countByExample(PartsExample example);

    int deleteByExample(PartsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Parts record);

    int insertSelective(Parts record);

    List<Parts> selectByExample(PartsExample example);

    List<Parts> findPageWithType();

    Parts selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Parts record, @Param("example") PartsExample example);

    int updateByExample(@Param("record") Parts record, @Param("example") PartsExample example);

    int updateByPrimaryKeySelective(Parts record);

    int updateByPrimaryKey(Parts record);

    List<Parts> findPageByPageNoAndQeryMap(Map<String, Object> queryMap);

    /**
     *  根据订单Id 查找订单配件及其数量
     * @param orderId 订单Id
     * @return
     */
    List<Parts> findPartsListByOrderPartsOrderId(Integer orderId);
}