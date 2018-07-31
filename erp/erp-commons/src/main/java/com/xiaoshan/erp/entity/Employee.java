package com.xiaoshan.erp.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author 
 */
public class Employee implements Serializable {

    /**
     * 状态：0.禁用 1正常
     */
    public static Integer EMPLOYEE_STATE_FROZEN = 0;
    public static Integer EMPLOYEE_STATE_NORMAL = 1;

    /**
     * 员工ID
     */
    private Integer id;

    /**
     * 员工姓名
     */
    private String employeeName;

    /**
     * 员工电话
     */
    private String employeeTel;

    /**
     * 密码
     */
    private String password;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 状态：0.禁用 1正常
     */
    private Integer state;

    /**
     * 当前员工拥有的角色(部门)列表
     */
    private List<Role> roleList;

    private static final long serialVersionUID = 1L;

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeTel() {
        return employeeTel;
    }

    public void setEmployeeTel(String employeeTel) {
        this.employeeTel = employeeTel;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", employeeName='" + employeeName + '\'' +
                ", employeeTel='" + employeeTel + '\'' +
                ", password='" + password + '\'' +
                ", createTime=" + createTime +
                ", state=" + state +
                ", roleList=" + roleList +
                '}';
    }
}