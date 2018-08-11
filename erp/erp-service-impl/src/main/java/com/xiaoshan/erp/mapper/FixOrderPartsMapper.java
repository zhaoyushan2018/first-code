package com.xiaoshan.erp.mapper;

import com.xiaoshan.erp.entity.FixOrderParts;
import com.xiaoshan.erp.entity.FixOrderPartsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FixOrderPartsMapper {
    long countByExample(FixOrderPartsExample example);

    int deleteByExample(FixOrderPartsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(FixOrderParts record);

    int insertSelective(FixOrderParts record);

    List<FixOrderParts> selectByExample(FixOrderPartsExample example);

    FixOrderParts selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") FixOrderParts record, @Param("example") FixOrderPartsExample example);

    int updateByExample(@Param("record") FixOrderParts record, @Param("example") FixOrderPartsExample example);

    int updateByPrimaryKeySelective(FixOrderParts record);

    int updateByPrimaryKey(FixOrderParts record);
}