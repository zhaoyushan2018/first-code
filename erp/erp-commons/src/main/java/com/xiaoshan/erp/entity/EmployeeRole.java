package com.xiaoshan.erp.entity;

import java.io.Serializable;

/**
 * @author 
 */
public class EmployeeRole implements Serializable {
    /**
     * ID
     */
    private Integer id;

    /**
     * 员工ID
     */
    private Integer employeeId;

    /**
     * 角色ID
     */
    private Integer roleId;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
}