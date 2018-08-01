package com.xiaoshan.erp.service;

import com.xiaoshan.erp.entity.Permission;
import com.xiaoshan.erp.entity.Role;
import com.xiaoshan.erp.exception.ServiceException;

import java.util.List;
import java.util.Map;

/**
 * rolePermissiopn角色权限
 * @author YushanZhao
 * @Date:2018/7/27
 */
public interface RolePermissionService {

    /**
     * 通过对象保存权限
     * @param permission 权限对象
     */
    void savePermission(Permission permission);

    /**
     * 查找全部权限列表
     * @return 以List集合方式返回
     */
    List<Permission> findAllPermission();

    /**
     * 根据权限id删除对象
     * @param id 权限id
     */
    void delPermissionById(Integer id) throws ServiceException;

    /**
     *
     * 根据id查找对象
     * @param id 权限id
     * @return 权限对象
     * @throws ServiceException
     */
    Permission findPermissionById(Integer id) throws ServiceException;

    /**
     * 根据权限类型(菜单\表单) 查找对应的List集合
     * @param permissionTypeMenu 菜单
     * @return 返回List集合
     */
    List<Permission> findPermissionByType(String permissionTypeMenu);

    /**
     * 保存修改后的Permission对象
     * @param permission 修改后的对象
     */
    void updatePermission(Permission permission);

    /**
     * 保存角色 和角色权限
     * @param role 角色
     * @param permissionIds 角色权限Id 数组
     */
    void saveRole(Role role, Integer[] permissionIds);

    /**
     * 查询所有角色列表(包括每个角色对应的权限列表)
     * @return
     */
    List<Role> findListRoleLiftPermission();

    /**
     * 根据权限名字查找权限 存在false 不存在返回true
     * @param permissionName 权限名字
     * @return 返回boolean类型 true|false
     */
    boolean findPermissionByName(String permissionName);

    /**
     * 根据权限名字查找权限 存在(是自己true, 不是自己false) 不存在返回true
     * @param permissionName 权限名字
     * @return 返回boolean类型 true|false
     * @param id  要修改的id
     */
    boolean findPermissionByNameAndOwn(Integer id ,String permissionName);

    /**
     * 根据id查找role(角色对象)
     * @param id 角色id
     * @return Role对象
     */
    Role findRoleById(Integer id);

    /**
     *  获得Permission(权限)列表,当前角色拥有的话,为true, 没有为false
     *  在编辑页面判断当前权限的复选框是否被选中
     * @param rolePermissionList 当前角色拥有的权限
     * @return 有顺序的map集合 如果被选择value为true
     */
    Map<Permission,Boolean> permissionBooleanMap(List<Permission> rolePermissionList);

    /**
     * 根据角色id查找角色(和当前角色拥有的权限)
     * @param id 角色id
     * @return 返回角色和角色权限集合
     */
    Role findRoleLeftPernissionById(Integer id);

    /**
     * 验证角色名字是否存在
     * @param roleName
     * @return
     */
    Boolean checkRoleName(String roleName);

    /**
     * 验证角色名称(除自己外)是否重复
     * @param id
     * @param roleName
     * @return 返回 重复(不可用false) 不重复(可用true)
     */
    Boolean checkUpdateRoleName(Integer id, String roleName);

    /**
     * 修改 角色
     * @param role 角色对象
     * @param permissionId 角色权限数组
     */
    void updateRole(Role role, Integer[] permissionId);

    /**
     * 根据id删除对应角色
     * @param id 角色id
     * @throws ServiceException
     */
    void delRoleById(Integer id) throws ServiceException;

    /**
     * 获取当前权限的所有子权限(菜单)
     * @param permission 当前权限
     * @return 当前权限的所有子权限的列表(菜单)
     */
    List<Permission> findAllPermissionSon(Permission permission);

    /**
     * 根据员工id 查找该员工角色集合
     * @param id employeeId
     * @return List<Role>
     */
    List<Role> findRoleListByEmployeeId(Integer id);

    /**
     * 根据角色id获得当前角色的全部权限集合
     * 根据roleId 获得List<Permission>
     * @param roleId 角色id
     * @return 该角色的全部权限集合
     */
    List<Permission> findAllPermissionListByRoleId(Integer roleId);

    /**
     * 查找所有角色(职位)列表
     * @return 角色集合List<Role>
     */
    List<Role> findAllRoleList();

    /**
     * 验证权限类型 根据权限id (如果该权限下面有子权限则不能修改为按钮,只能为菜单)
     * @param permissionId 权限Id
     * @param permissionType 权限类型()
     * @return
     */
    boolean checkPermissionType(Integer permissionId, String permissionType);
}
