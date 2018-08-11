package com.xiaoshan.erp.service;

import com.github.pagehelper.PageInfo;
import com.xiaoshan.erp.entity.Parts;
import com.xiaoshan.erp.entity.Type;

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
    PageInfo<Parts> findPageByPageNoAndQeryMap(Integer pageNo, Map<String, Object> queryMap);

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


    /**
     * 根据配件编号查找对象parts
     * @param partsNo
     * @return
     */
    List<Parts> findPartsByPartsNo(String partsNo);


    /**
     *  新增配件类型 根据配件类型 验证是否重复
     * @param addTypeName 配件类型名字
     * @return 重复false(不可用)  不重复true(可用)
     */
    boolean checkAddTypeName(String addTypeName);

    /**
     * 根据(配件类型名字) 新增配件类型
     * @param addTypeName 配件类型名字
     */
    void savePartsType(String addTypeName);

    /**
     * 根据配件类型id 查找配件对应的配件类型
     * @param id 配件类型id
     * @return 配件类型对象
     */
    Type findPartsTypeByPartsTypeId(Integer id);

    /**
     *  根据配件类型id 查找该类型下的所有配件
     * @param id 配件类型id
     * @return 该类型下所有的配件
     */
    List<Parts> findAllPartsByPartsTypeId(Integer id);

    /**
     *  查找该订单的所需配件列表
     * @param orderId 订单Id
     * @return
     */
    List<Parts> findPartsByOrderPartsOrderId(Integer orderId);

    /**
     *  根据json数据修改库存
     * @param json
     */
    void updateInventory(String json);
}
