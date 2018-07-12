package com.xiaoshan.mapper;

import com.xiaoshan.entity.Movie;
import java.util.List;

public interface MovieMapper {

    /**
     * 根据id删除对应movie对象
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 插入movie对象
     * @param record
     * @return
     */
    int insert(Movie record);

    /**
     * 查询movie对象根据id
     * @param id
     * @return
     */
    Movie selectByPrimaryKey(Integer id);

    /**
     * 查询全部movie对象
     * @return
     */
    List<Movie> selectAll();

    /**
     * 根据movie对象修改
     * 没有就是null
     * @param record
     * @return
     */
    int updateByPrimaryKey(Movie record);
}