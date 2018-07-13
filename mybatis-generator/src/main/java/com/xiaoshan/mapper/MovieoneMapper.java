package com.xiaoshan.mapper;

import com.xiaoshan.entity.Movieone;
import com.xiaoshan.entity.MovieoneExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MovieoneMapper {
    long countByExample(MovieoneExample example);

    /**
     * 可以根据任意条件删除
     * @param example
     * @return
     */
    int deleteByExample(MovieoneExample example);

    /**
     * 根据id删除对应Movieone对象
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *  增加数据(movieone对象)
     *  返回受影响的行数
     * @param record
     * @return
     */
    int insert(Movieone record);

    int insertSelective(Movieone record);

    /**
     *  创建MovieoneExample实体类(直接当参数擦查询全部)
     *   movieoneExample.createCriteria();  criteria.and...相当于加条件
     *   可以查询全部,也可以根据条件查找
     * @param example
     * @return
     */
    List<Movieone> selectByExample(MovieoneExample example);

    /**
     * 根据id查询movieone对象,返回
     * @param id
     * @return
     */
    Movieone selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Movieone record, @Param("example") MovieoneExample example);

    int updateByExample(@Param("record") Movieone record, @Param("example") MovieoneExample example);

    /**
     * 根据movieone对象里的id修改(有多少内容修改多少)
     * 不赋值 不会变成null
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(Movieone record);

    /**
     * 先查找,防止是null
     * 根据Movieone里面的id修改内容
     * @param record
     * @return
     */
    int updateByPrimaryKey(Movieone record);
}