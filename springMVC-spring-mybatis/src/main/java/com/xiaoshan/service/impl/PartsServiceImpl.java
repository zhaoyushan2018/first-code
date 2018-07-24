package com.xiaoshan.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiaoshan.entity.Parts;
import com.xiaoshan.entity.PartsExample;
import com.xiaoshan.mapper.PartsMapper;
import com.xiaoshan.service.PartsService;
import com.xiaoshan.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
