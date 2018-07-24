package com.xiaoshan.service;

import com.xiaoshan.entity.Parts;

/**
 * @author YushanZhao
 * @Date:2018/7/23
 */
public interface PartsService {

    /**
     * 根据id查找parts对象返回
     * @param id
     * @return
     */
    Parts findPartsById(Integer id);
}
