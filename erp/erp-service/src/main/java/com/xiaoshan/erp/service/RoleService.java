package com.xiaoshan.erp.service;

import com.xiaoshan.erp.entity.Role;

import java.util.List;

/**
 * @author YushanZhao
 * @Date:2018/7/26
 */
public interface RoleService {

    /**
     * 查找所有员工角色
     * @return 返回Lits集合
     */
    List<Role> findAllRoleList();
}
