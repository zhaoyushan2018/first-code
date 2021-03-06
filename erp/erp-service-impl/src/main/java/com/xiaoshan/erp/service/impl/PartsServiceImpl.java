package com.xiaoshan.erp.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.xiaoshan.erp.dto.FixOrderPartsDto;
import com.xiaoshan.erp.entity.*;
import com.xiaoshan.erp.mapper.PartsMapper;
import com.xiaoshan.erp.mapper.PartsStreamMapper;
import com.xiaoshan.erp.mapper.TypeMapper;
import com.xiaoshan.erp.service.PartsService;
import com.xiaoshan.erp.util.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author YushanZhao
 * @Date:2018/7/23
 */
@Service
public class PartsServiceImpl implements PartsService {

    Logger logger = LoggerFactory.getLogger(PartsServiceImpl.class);

    @Autowired
    private PartsMapper partsMapper;

    @Autowired
    private TypeMapper typeMapper;

    @Autowired
    private PartsStreamMapper partsStreamMapper;

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

    /**
     * 根据页码查找List集合对象
     *
     * @param pageNo 页码
     * @return pageInfo对象
     */
    @Override
    public PageInfo<Parts> findPage(Integer pageNo) {



        //开始位置，要找多少个(第几页,取几条数据)
        PageHelper.startPage(pageNo,Constant.DEFAULT_PAGE_SIZE);

        //从几开始,取几条数据
        //PageHelper.offsetPage((pageNo - 1)* Constant.DEFAULT_PAGE_SIZE, Constant.DEFAULT_PAGE_SIZE);
        PartsExample partsExample = new PartsExample();
        //排序,正序(倒序desc)
        partsExample.setOrderByClause("id asc");
        //不适用生成的，因为不能多表联查。使用自定义的
        //List<Parts> partsList = partsMapper.selectByExample(partsExample);
        List<Parts> partsList = partsMapper.findPageWithType();

        //封装分页对象
        PageInfo<Parts> partsPageInfo = new PageInfo<>(partsList);

        return partsPageInfo;
    }

    /**
     * 增加parts对象到数据库
     *
     * @param parts
     */
    @Override
    public void addParts(Parts parts) {
        partsMapper.insert(parts);
    }

    /**
     * 根据id删除对应Parts对象
     *
     * @param id
     */
    @Override
    public void deletePartsById(Integer id) {
        partsMapper.deleteByPrimaryKey(id);
    }

    /**
     * 查找全部配件类型
     *
     * @return typeList
     */
    @Override
    public List<Type> findTypeList() {
        TypeExample typeExample = new TypeExample();
        List<Type> typeList = typeMapper.selectByExample(typeExample);

        return typeList;
    }

    /**
     * 根据页码和筛选条件map集合查询对应的配件列表
     *
     * @param pageNo   页码
     * @param queryMap 筛选条件map集合
     * @return
     */
    @Override
    public PageInfo<Parts> findPageByPageNoAndQeryMap(Integer pageNo, Map<String, Object> queryMap) {
        //开始位置，要找多少个(第几页,取几条数据)
        PageHelper.startPage(pageNo,Constant.DEFAULT_PAGE_SIZE);

        List<Parts> partsList = partsMapper.findPageByPageNoAndQeryMap(queryMap);
        PageInfo<Parts> partsPageInfo = new PageInfo<>(partsList);

        return partsPageInfo;
    }

    /**
     * 配件入库
     * 保存parts对象
     *
     * @param parts
     */
    @Override
    public void saveParts(Parts parts) {
        //有的话就挨个添加
        partsMapper.insertSelective(parts);
        //Integer id = parts.getId();//主键值
        //输出对象,调用toString方法
        logger.debug("新增的配件为:{}",parts);
    }

    /**
     * 根据id删除配件
     *
     * @param id
     */
    @Override
    public void delPartsById(Integer id) {
        //如果parts不等于null代表存在
        Parts parts = partsMapper.selectByPrimaryKey(id);
        if(parts != null){
            partsMapper.deleteByPrimaryKey(id);
        }
        logger.debug("删除配件成功:{}",parts);
    }

    /**
     * 更新parts对象
     *
     * @param parts
     */
    @Override
    public void editParts(Parts parts) {
        partsMapper.updateByPrimaryKeySelective(parts);
        logger.debug("更新配件:{}", parts);
    }

    /**
     * 根据配件编号查找对象parts
     *
     * @param partsNo
     * @return
     */
    @Override
    public List<Parts> findPartsByPartsNo(String partsNo) {
        PartsExample partsExample = new PartsExample();
        partsExample.createCriteria().andPartsNoEqualTo(partsNo);
        //Parts parts = (Parts) partsMapper.selectByExample(partsExample);
        List<Parts> partsList = partsMapper.selectByExample(partsExample);

        return partsList;
    }

    /**
     * 新增配件类型 根据配件类型 验证是否重复
     *
     * @param addTypeName 配件类型名字
     * @return 重复false(不可用)  不重复true(可用)
     */
    @Override
    public boolean checkAddTypeName(String addTypeName) {
        //根据类型名字 到数据库查找对应的全部对象
        TypeExample typeExample = new TypeExample();
        typeExample.createCriteria().andTypeNameEqualTo(addTypeName);
        List<Type> typeList = typeMapper.selectByExample(typeExample);

        if(typeList != null && typeList.size() > 0){
            return false;
        }

        return true;
    }

    /**
     * 根据(配件类型名字) 新增配件类型
     *
     * @param addTypeName 配件类型名字
     */
    @Override
    public void savePartsType(String addTypeName) {
        Type type = new Type();
        type.setTypeName(addTypeName);
        typeMapper.insertSelective(type);
    }

    /**
     * 根据配件类型id 查找配件对应的配件类型
     *
     * @param id 配件类型id
     * @return 配件类型对象
     */
    @Override
    public Type findPartsTypeByPartsTypeId(Integer id) {
        Type type = typeMapper.selectByPrimaryKey(id);

        return type;
    }

    /**
     * 根据配件类型id 查找该类型下的所有配件
     *
     * @param id 配件类型id
     * @return 该类型下所有的配件
     */
    @Override
    public List<Parts> findAllPartsByPartsTypeId(Integer id) {
        PartsExample partsExample = new PartsExample();
        partsExample.createCriteria().andTypeIdEqualTo(id);
        List<Parts> partsList = partsMapper.selectByExample(partsExample);

        return partsList;
    }

    /**
     * 查找该订单的所需配件列表
     *
     * @param orderId 订单Id
     * @return
     */
    @Override
    public List<Parts> findPartsByOrderPartsOrderId(Integer orderId) {
        List<Parts> partsList = partsMapper.findPartsListByOrderPartsOrderId(orderId);

        return partsList;
    }

    /**
     * 根据json数据修改库存
     *
     * @param json
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void updateInventory(String json) {
        FixOrderPartsDto fixOrderPartsDto = new Gson().fromJson(json, FixOrderPartsDto.class);
        for(FixOrderParts fixOrderParts : fixOrderPartsDto.getFixOrderPartsList()){
            //更新库存
            Parts parts = partsMapper.selectByPrimaryKey(fixOrderParts.getPartsId());
            if(parts == null){
                logger.error("数据传输的配件id:{},参数异常或者配件不存在...", fixOrderParts.getPartsId());
            }
            parts.setInventory(parts.getInventory() - fixOrderParts.getPartsNum());
            partsMapper.updateByPrimaryKeySelective(parts);

            // 生成出库流水  //封装出库流水,并存数据库
            PartsStream partsStream = new PartsStream();
            partsStream.setOrderId(fixOrderParts.getOrderId());
            partsStream.setEmployeeId(fixOrderPartsDto.getEmployeeId());
            partsStream.setPartsId(fixOrderParts.getPartsId());
            partsStream.setNum(fixOrderParts.getPartsNum());
            partsStream.setType(PartsStream.PARTS_STREAM_TYPE_OUT);

            partsStreamMapper.insertSelective(partsStream);
            logger.info("{} ---> 配件出库", partsStream);
        }

    }












}
