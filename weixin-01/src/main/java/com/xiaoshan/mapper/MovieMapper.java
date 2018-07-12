package com.xiaoshan.mapper;

import com.xiaoshan.entity.Movie;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author YushanZhao
 * @Date:2018/7/11
 */
public interface MovieMapper {

    /**
     * 根据关键字查找
     * 返回movieList
     * @param key 关键字
     * @return
     */
    List<Movie> findMovieByKey(@Param("key") String key);

    /**
     * 根据多个id
     * 查找多个movie对象
     * 并返回movieList
     * @param idList
     * @return
     */
    List<Movie> findMovieByIdList(@Param("idList") List<Integer> idList);

    /**
     * 根据参数里面的关键字查找movie返回
     * @param queryKeys(多个keys)
     * @return
     */
    List<Movie> findByParam(Map<String, Object> queryKeys);

    /**
     * 根据id查询movie对象
     * @param id
     * @return
     */
    Movie findMovieById(Integer id);









}
