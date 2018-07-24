package com.xiaoshan.service.impl;

import com.xiaoshan.entity.Parts;
import com.xiaoshan.mapper.PartsMapper;
import com.xiaoshan.service.PartsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author YushanZhao
 * @Date:2018/7/23
 */
@Service
public class PartsServiceImpl implements PartsService {

    @Autowired
    private PartsMapper partsMapper;

    /**
     * 根据id查找parts对象返回
     *
     * @param id
     * @return
     */
    @Override
    public Parts findPartsById(Integer id) {
        return partsMapper.selectByPrimaryKey(id);
    }
}
