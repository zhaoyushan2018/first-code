package com.xiaoshan.service;

import com.github.pagehelper.PageInfo;
import com.xiaoshan.entity.Type;

/**
 * @author YushanZhao
 * @Date:2018/7/25
 */
public interface TypeService {

    /**
     * 根据页码 查找页码的PartsType类型的List集合
     * @param pageNo
     * @return
     */
    PageInfo<Type> findTypePageList(Integer pageNo);

    /**
     * 根据配件类型id删除对应类型
     * @param id
     */
    void delTypeById(Integer id);
}
