package com.xiaoshan.mapper;

import com.xiaoshan.entity.Movie;
import com.xiaoshan.entity.Type;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author YushanZhao
 * @Date:2018/7/10
 */
public interface MovieManyMapper {

    /**
     * Movie To User 为 多对一
     * 根据movieid查找对应的movie和user
     * @param id
     * @return
     * 没查找的输出为null
     */
    Movie findUserByMovieId(Integer id);

    /**
     * 四标联查返回Movie对象
     * @param id
     * @return
     */
    Movie findUserTypeByMovieId(Integer id);

    /**
     * 批量添加type
     * @param typeList
     * @return
     */
    int addTypeBatch(@Param("typeList") List<Type> typeList);

}
