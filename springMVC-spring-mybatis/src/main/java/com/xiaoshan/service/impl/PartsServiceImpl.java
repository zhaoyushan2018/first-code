package com.xiaoshan.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiaoshan.entity.Parts;
import com.xiaoshan.entity.PartsExample;
import com.xiaoshan.entity.Type;
import com.xiaoshan.entity.TypeExample;
import com.xiaoshan.mapper.PartsMapper;
import com.xiaoshan.mapper.TypeMapper;
import com.xiaoshan.service.PartsService;
import com.xiaoshan.util.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
