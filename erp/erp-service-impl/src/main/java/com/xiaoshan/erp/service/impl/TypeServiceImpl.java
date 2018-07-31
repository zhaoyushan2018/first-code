package com.xiaoshan.erp.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiaoshan.erp.entity.Parts;
import com.xiaoshan.erp.entity.PartsExample;
import com.xiaoshan.erp.entity.Type;
import com.xiaoshan.erp.entity.TypeExample;
import com.xiaoshan.erp.mapper.PartsMapper;
import com.xiaoshan.erp.mapper.TypeMapper;
import com.xiaoshan.erp.service.TypeService;
import com.xiaoshan.erp.util.Constant;
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
