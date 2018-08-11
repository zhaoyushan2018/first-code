package com.xiaoshan.erp.mapper;

import com.xiaoshan.erp.entity.ServiceType;
import com.xiaoshan.erp.entity.ServiceTypeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ServiceTypeMapper {
    long countByExample(ServiceTypeExample example);

    int deleteByExample(ServiceTypeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ServiceType record);

    int insertSelective(ServiceType record);

    List<ServiceType> selectByExample(ServiceTypeExample example);

    ServiceType selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ServiceType record, @Param("example") ServiceTypeExample example);

    int updateByExample(@Param("record") ServiceType record, @Param("example") ServiceTypeExample example);

    int updateByPrimaryKeySelective(ServiceType record);

    int updateByPrimaryKey(ServiceType record);
}