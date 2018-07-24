package com.xiaoshan.service;

import com.github.pagehelper.PageInfo;
import com.xiaoshan.entity.Parts;

/**
 * @author YushanZhao
 * @Date:2018/7/23
 */
public interface PartsService {

    /**
     * 根据id查找对应的parts配件对象返回
     * @param id
     * @return
     */
    Parts findPartsById(Integer id);

    /**
     * 根据页码查找List集合对象
     * @param pageNo 页码
     * @return pageInfo对象
     */
    PageInfo<Parts> findPage(Integer pageNo);
}
