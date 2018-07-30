package com.xiaoshan.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiaoshan.entity.Parts;
import com.xiaoshan.entity.PartsExample;
import com.xiaoshan.entity.Type;
import com.xiaoshan.entity.TypeExample;
import com.xiaoshan.mapper.PartsMapper;
import com.xiaoshan.mapper.TypeMapper;
import com.xiaoshan.service.TypeService;
import com.xiaoshan.util.Constant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author YushanZhao
 * @Date:2018/7/25
 */
@Service
public class TypeServiceImpl implements TypeService {

    @Autowired
    private TypeMapper typeMapper;

    @Autowired
    private PartsMapper partsMapper;


    /**
     * 根据页码 查找页码的PartsType类型的List集合
     *
     * @param pageNo
     * @return
     */
    @Override
    public PageInfo<Type> findTypePageList(Integer pageNo) {
        //开始位置，要找多少个(第几页,取几条数据)
        PageHelper.startPage(pageNo,Constant.DEFAULT_PAGE_SIZE);

        TypeExample typeExample = new TypeExample();
        List<Type> typeList = typeMapper.selectByExample(typeExample);
        PageInfo<Type> typePageInfo = new PageInfo<>(typeList);

        return typePageInfo;
    }

    /**
     * 根据配件类型id删除对应类型
     *
     * @param id
     */
    @Override
    public void delTypeById(Integer id) {
        Type type = typeMapper.selectByPrimaryKey(id);
        if(type != null){
            PartsExample partsExample = new PartsExample();
            partsExample.createCriteria().andTypeIdEqualTo(id);
            List<Parts> partsList = partsMapper.selectByExample(partsExample);
            if(partsList.isEmpty()){
                typeMapper.deleteByPrimaryKey(id);
            } else {

            }
        } else {

        }


    }
}
