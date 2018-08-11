package com.xiaoshan.erp.service.impl;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiaoshan.erp.entity.*;
import com.xiaoshan.erp.exception.ServiceException;
import com.xiaoshan.erp.mapper.EmployeeRoleMapper;
import com.xiaoshan.erp.mapper.PermissionMapper;
import com.xiaoshan.erp.mapper.RoleMapper;
import com.xiaoshan.erp.mapper.RolePermissionMapper;
import com.xiaoshan.erp.service.RolePermissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author YushanZhao
 * @Date:2018/7/27
 */
@Service
public class RolePermissionServiceiml implements RolePermissionService {

    Logger logger = LoggerFactory.getLogger(RolePermissionServiceiml.class);

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Autowired
    private EmployeeRoleMapper employeeRoleMapper;

    /**
     * 通过对象保存权限
     *
     * @param permission 权限对象
     */
    @Override
    public void savePermission(Permission permission) {
        permissionMapper.insertSelective(permission);
    }

    /**
     * 查找全部权限列表
     *
     * @return 以List集合方式返回
     */
    @Override
    public List<Permission> findAllPermission() {
        PermissionExample permissionExample = new PermissionExample();
        List<Permission> permissionList = permissionMapper.selectByExample(permissionExample);
        List<Permission> endList = new ArrayList<>();
        treeList(permissionList, endList, 0);
        return endList;
    }

    /**
     * 将查询数据库的角色列表转换为树形集合
     * @param permissionList 数据库直接查询出的集合
     * @param endList 转换为树形的结果集合
     * @param parentId 父ID
     */
    private void treeList(List<Permission> permissionList, List<Permission> endList, int parentId){
        List<Permission> tempList = Lists.newArrayList(Collections2.filter(permissionList, new Predicate<Permission>() {
            @Override
            public boolean apply(@Nullable Permission permission) {
                return permission.getPid().equals(parentId);
            }
        }));
        for (Permission permission : tempList){
            endList.add(permission);
            treeList(permissionList, endList, permission.getId());
        }

    }


    /**
     * 根据权限id删除对象
     *
     * @param id 权限id
     */
    @Override
    public void delPermissionById(Integer id) throws ServiceException{
        //先判断参数是否异常,根据参数id,查找数据库 是否有相应对象 没有则抛异常 有则继续
        Permission permission = permissionMapper.selectByPrimaryKey(id);
        if (permission == null){
            throw new ServiceException("参数异常,请稍后重试...");
        }
        //判断是否时父权限, 判断是否拥有子权限, 如果拥有子权限 , 则不能删除
        PermissionExample permissionExample = new PermissionExample();
        //添加查找条件,父权限id为子权限的pid, 所以查找要删除id的pid, 如果是空,则代表没有子权限(如果不等于null并且List长度大于0则有子权限)
        permissionExample.createCriteria().andPidEqualTo(id);

        List<Permission> permissionList = permissionMapper.selectByExample(permissionExample);
        if(permissionList != null && permissionList.size() > 0){
            //permissionList不等于null并且permissionList长度大于零
            throw new ServiceException("该权限有子权限,不能删除...");
        }

        //查看该权限是否已被角色占用(如果权限被角色使用则不能删除)
        RolePermissionExample rolePermissionExample = new RolePermissionExample();
        rolePermissionExample.createCriteria().andPermissionIdEqualTo(id);
        List<RolePermission> rolePermissionList = rolePermissionMapper.selectByExample(rolePermissionExample);

        if (rolePermissionList != null && rolePermissionList.size() > 0){
            //不等于null,并且长度大于零  说明该权限已被角色占用
            throw new ServiceException("该权限已有角色正在使用,不能删除...");
        }

        permissionMapper.deleteByPrimaryKey(id);
    }

    /**
     * 根据id查找对象
     *
     * @param id 权限id
     * @return 权限对象
     */
    @Override
    public Permission findPermissionById(Integer id) throws ServiceException{
        Permission permission = permissionMapper.selectByPrimaryKey(id);
        if (permission == null){
            throw new ServiceException("参数异常,请稍后重试...");
        } else {
            return permission;
        }
    }

    /**
     * 根据权限类型(菜单\表单) 查找对应的List集合
     *
     * @param permissionTypeMenu 菜单
     * @return 返回List集合
     */
    @Override
    public List<Permission> findPermissionByType(String permissionTypeMenu) {
        PermissionExample permissionExample = new PermissionExample();
        //添加条件
        permissionExample.createCriteria().andPermissionTypeEqualTo(permissionTypeMenu);
        List<Permission> permissionList = permissionMapper.selectByExample(permissionExample);

        return permissionList;
    }

    /**
     * 保存修改后的Permission对象
     *
     * @param permission 修改后的对象
     */
    @Override
    public void updatePermission(Permission permission) {
        permissionMapper.updateByPrimaryKeySelective(permission);
    }

    /**
     * 保存角色 和角色权限
     *
     * @param role         角色
     * @param permissionIds 角色权限Id 数组
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void saveRole(Role role, Integer[] permissionIds) {
        //新增角色
        roleMapper.insertSelective(role);
        //获取保存角色的自增长id(xml加入useGeneratedKeys="true" keyProperty="id")
        Integer roleId = role.getId();
        System.out.println(roleId);

        //新增角色权限关联关系表
        for(Integer permissionId : permissionIds){
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(roleId);
            rolePermission.setPermissionId(permissionId);
            System.out.println(rolePermission);

            rolePermissionMapper.insertSelective(rolePermission);
        }
    }

    /**
     * 查询所有角色列表(包括每个角色对应的权限列表)
     *
     * @return
     */
    @Override
    public List<Role> findListRoleLiftPermission() {
        List<Role> roleList = roleMapper.findListRoleLiftPermission();


        return roleList;
    }

    /**
     * 根据权限名字查找权限 存在false 不存在返回true
     *
     * @param permissionName 权限名字
     * @return 返回boolean类型 true|false
     */
    @Override
    public boolean findPermissionByName(String permissionName) {
        //以permissionName为条件查找 对象集合
        PermissionExample permissionExample = new PermissionExample();
        permissionExample.createCriteria().andPermissionNameEqualTo(permissionName);
        List<Permission> permissionList = permissionMapper.selectByExample(permissionExample);

            if(permissionList != null && permissionList.size() > 0){
                //已经存在
                return false;
        }
        //没有
        return true;
    }

    /**
     * 根据权限名字查找权限 存在(是自己true, 不是自己false) 不存在返回true
     *
     * @param permissionName 权限名字
     * @return 返回boolean类型 true|false
     * @param id  要修改的权限id
     */
    @Override
    public boolean findPermissionByNameAndOwn(Integer id, String permissionName) {
        Permission permissionUpdate = permissionMapper.selectByPrimaryKey(id);
        //以permissionName为条件查找 对象集合
        PermissionExample permissionExample = new PermissionExample();
        permissionExample.createCriteria().andPermissionNameEqualTo(permissionName);
        List<Permission> permissionList = permissionMapper.selectByExample(permissionExample);
        //System.out.println("长度为: " + permissionList.size() + "第一个权限的id: " + permissionList.get(0).getId());

        if(permissionList != null && permissionList.size() > 0 && !permissionList.get(0).getId().equals(id)){
            //已经存在
            return false;
        }
        //没有
        return true;
    }

    /**
     * 根据id查找role(角色对象)
     *
     * @param id 角色id
     * @return Role对象
     */
    @Override
    public Role findRoleById(Integer id) {
        Role role = roleMapper.selectByPrimaryKey(id);
        return role;
    }

    /**
     * 获得Permission(权限)列表,当前角色拥有的话,为true, 没有为false
     * 在编辑页面判断当前权限的复选框是否被选中
     *
     * @param rolePermissionList 当前角色拥有的权限
     * @return 有顺序的map集合 如果被选择value为true
     */
    @Override
    public Map<Permission, Boolean> permissionBooleanMap(List<Permission> rolePermissionList) {
        //所有权限列表, 上面已定义的方法(已经转换为树形集合)
        List<Permission> permissionList = findAllPermission();

        //获得有序的map集合(google里面的)
        Map<Permission, Boolean> permissionBooleanMap = Maps.newLinkedHashMap();

        for (Permission permission : permissionList){
            boolean flag = false;
            for (Permission rolePermission : rolePermissionList){
                if(permission.getId().equals(rolePermission.getId())){
                    flag = true;
                    break;
                }
            }
            permissionBooleanMap.put(permission,flag);
        }

        return permissionBooleanMap;
    }

    /**
     * 根据角色id查找角色(和当前角色拥有的权限)
     *
     * @param id 角色id
     * @return 返回角色和角色权限集合
     */
    @Override
    public Role findRoleLeftPernissionById(Integer id) {
        Role role = roleMapper.findRoleLiftPermissionByRoleId(id);

        return role;
    }

    /**
     * 验证角色名字是否存在
     *
     * @param roleName
     * @return
     */
    @Override
    public Boolean checkRoleName(String roleName) {
        RoleExample roleExample = new RoleExample();
        roleExample.createCriteria().andRoleNameEqualTo(roleName);
        List<Role> roleList = roleMapper.selectByExample(roleExample);

        if (roleList != null && roleList.size() > 0){
            return false;
        }
        return true;
    }

    /**
     * 验证角色名称(除自己外)是否重复
     *
     * @param id
     * @param roleName
     * @return 返回 重复(不可用false) 不重复(可用true)
     */
    @Override
    public Boolean checkUpdateRoleName(Integer id, String roleName) {
        RoleExample roleExample = new RoleExample();
        roleExample.createCriteria().andRoleNameEqualTo(roleName);
        List<Role> roleList = roleMapper.selectByExample(roleExample);

        if(roleList != null && roleList.size() > 0){
            if(roleList.get(0).getId().equals(id)){
                return true;
            }
            return  false;
        }
        return true;
    }

    /**
     * 修改 角色
     *
     * @param role         角色对象
     * @param permissionIds 角色权限数组
     */
    @Override
    public void updateRole(Role role, Integer[] permissionIds) {
        //删除角色和权限对应关联关系
        RolePermissionExample rolePermissionExample = new RolePermissionExample();
        rolePermissionExample.createCriteria().andRoleIdEqualTo(role.getId());
        rolePermissionMapper.deleteByExample(rolePermissionExample);

        //重新创建 角色和权限的对应关联关系
        for(Integer permissionId : permissionIds){
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(role.getId());
            rolePermission.setPermissionId(permissionId);

            rolePermissionMapper.insertSelective(rolePermission);
        }

        //更新 角色
        roleMapper.updateByPrimaryKeySelective(role);

        logger.debug("更新角色为: " + role);
    }

    /**
     * 根据id删除对应角色
     *
     * @param id 角色id
     * @throws ServiceException
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void delRoleById(Integer id) throws ServiceException{
        //查看角色是否被账号(员工)引用,如果引用则不能删除
        EmployeeRoleExample employeeRoleExample = new EmployeeRoleExample();
        employeeRoleExample.createCriteria().andRoleIdEqualTo(id);
        //关联关系表employeeRole里面 根据条件roleId 查找出来的集合
        List<EmployeeRole> employeeRoleList = employeeRoleMapper.selectByExample(employeeRoleExample);
        /*if(employeeRoleList != null && employeeRoleList.size() > 0){
            throw new ServiceException("该角色正在被账号使用,不能删除...");
        }*/
        if(employeeRoleList != null && !employeeRoleList.isEmpty()){
            throw new ServiceException("该角色正在被账号使用,不能删除...");
        }
        //上面没抛异常,说明角色和账号没有关联关系...
        //删除角色和权限的关联关系
        RolePermissionExample rolePermissionExample = new RolePermissionExample();
        rolePermissionExample.createCriteria().andRoleIdEqualTo(id);
        rolePermissionMapper.deleteByExample(rolePermissionExample);

        //根据id查找角色对象
        Role role = roleMapper.selectByPrimaryKey(id);
        if(role == null){
            throw new ServiceException("参数异常,删除失败...");
        } else {
            roleMapper.deleteByPrimaryKey(id);
            logger.debug("删除角色:{}", role);
        }

    }

    /**
     * 获取当前权限的所有子权限(菜单)
     *
     * @param permission 当前权限
     * @return 当前权限的所有子权限的列表(菜单)
     */
    @Override
    public List<Permission> findAllPermissionSon(Permission permission) {
        PermissionExample permissionExample = new PermissionExample();
        permissionExample.createCriteria().andPidEqualTo(permission.getId()).andPermissionTypeEqualTo(Permission.PERMISSION_TYPE_MENU);
        List<Permission> sonPermissionList = permissionMapper.selectByExample(permissionExample);


        return sonPermissionList;
    }

    /**
     * 根据员工id 查找该员工角色集合
     *
     * @param id employeeId
     * @return List<Role>
     */
    @Override
    public List<Role> findRoleListByEmployeeId(Integer id) {
        List<Role> nowRoleList = roleMapper.findRoleListByEmployeeId(id);

        return nowRoleList;
    }

    /**
     * 根据角色id获得当前角色的全部权限集合
     * 根据roleId 获得List<Permission>
     *
     * @param roleId 角色id
     * @return 该角色的全部权限集合
     */
    @Override
    public List<Permission> findAllPermissionListByRoleId(Integer roleId) {
        List<Permission> permissionList = permissionMapper.findAllPermissionListByRoleId(roleId);

        return permissionList;
    }

    /**
     * 查找所有角色(职位)列表
     *
     * @return 角色集合List<Role>
     */
    @Override
    public List<Role> findAllRoleList() {
        RoleExample roleExample = new RoleExample();
        List<Role> roleList = roleMapper.selectByExample(roleExample);

        return roleList;
    }

    /**
     * 验证权限类型 根据权限id (如果该权限下面有子权限则不能修改为按钮,只能为菜单)
     *
     * @param permissionId   权限Id
     * @param permissionType 权限类型()
     * @return
     */
    @Override
    public boolean checkPermissionType(Integer permissionId, String permissionType) {
        //根据权限id查找 该权限是否是父权限 如果是父权限 则权限类型只能为菜单
        //把权限id作为父权限查找
        PermissionExample permissionExample = new PermissionExample();
        permissionExample.createCriteria().andPidEqualTo(permissionId);
        List<Permission> permissionList = permissionMapper.selectByExample(permissionExample);
        if(permissionList != null && permissionList.size() > 0){
            if(permissionType.equals(Permission.PERMISSION_TYPE_MENU)){
                return true;
            }
        } else {
            return true;
        }

        return false;
    }


}
