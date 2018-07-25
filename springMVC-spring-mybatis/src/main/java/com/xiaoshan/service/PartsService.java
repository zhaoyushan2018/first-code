package com.xiaoshan.service;

import com.github.pagehelper.PageInfo;
import com.xiaoshan.entity.Parts;
import com.xiaoshan.entity.Type;

import java.util.List;
import java.util.Map;

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

    /**
     * 增加parts对象到数据库
     * @param parts
     */
    void addParts(Parts parts);

    /**
     * 根据id删除对应Parts对象
     * @param id
     */
    void deletePartsById(Integer id);

    /**
     * 查找全部配件类型
     * @return typeList
     */
    List<Type> findTypeList();

    /**
     *根据页码和筛选条件map集合查询对应的配件列表
     * @param pageNo 页码
     * @param queryMap 筛选条件map集合
     * @return
     */
    PageInfo<Parts> findPageByPageNoAndQeryMap(Integer pageNo, Map<String,Object> queryMap);

    /**
     * 配件入库
     * 保存parts对象
     * @param parts
     */
    void saveParts(Parts parts);

    /**
     * 根据id删除配件
     * @param id
     */
    void delPartsById(Integer id);

    /**
     * 更新parts对象
     * @param parts
     */
    void editParts(Parts parts);
}
