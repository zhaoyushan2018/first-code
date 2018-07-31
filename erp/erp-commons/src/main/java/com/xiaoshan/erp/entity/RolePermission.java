package com.xiaoshan.erp.entity;

import java.io.Serializable;

/**
 * @author 
 */
public class RolePermission implements Serializable {
    /**
     * ID
     */
    private Integer id;

    /**
     * 角色ID
     */
    private Integer roleId;

    /**
     * 权限ID
     */
    private Integer permissionId;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Integer permissionId) {
        this.permissionId = permissionId;
    }

    @Override
    public String toString() {
        return "RolePermission{" +
                "id=" + id +
                ", roleId=" + roleId +
                ", permissionId=" + permissionId +
                '}';
    }
}