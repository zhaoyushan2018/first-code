package com.xiaoshan.erp.service.impl;

import com.xiaoshan.erp.entity.Role;
import com.xiaoshan.erp.entity.RoleExample;
import com.xiaoshan.erp.mapper.RoleMapper;
import com.xiaoshan.erp.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author YushanZhao
 * @Date:2018/7/26
 */
@Service
public class RoleServiceiml implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    /**
     * 查找所有员工角色
     *
     * @return 返回Lits集合
     */
    @Override
    public List<Role> findAllRoleList() {
        RoleExample roleExample = new RoleExample();
        List<Role> roleList = roleMapper.selectByExample(roleExample);

        return roleList;
    }
}
