package com.xiaoshan.mapper;

import com.xiaoshan.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author YushanZhao
 * @Date:2018/7/10
 */
public interface UserMapper {

    /**
     * 新增对象
     * @param user  对象
     * @return 返回受影响的行数
     */
    int save(User user);

    /**
     * 根据id查找user对象返回
     * @param id
     * @return
     */
    User findUserById(Integer id);

    /**
     * 查询全部User对象,并返回
     * @return
     */
    List<User> findAll();

    /**
     * 根据id删除对相应对象
     * @param id
     */
    void deleteById(Integer id);

    /**
     * 修改User
     * @param user
     */
    void update(User user);

    /**
     * @param page 起始行
     * @param pageSize 每页数量
     * @return
     */
    List<User> findByPage(@Param("start") int start,@Param("pageSize") int pageSize);

    List<User> findPageByMap(Map<String, Integer> maps);













}
