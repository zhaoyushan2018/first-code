package com.xiaoshan.erp.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 
 */
public class EmployeeLoginLog implements Serializable {
    private Integer id;

    private String loginIp;

    private Date loginTime;

    private Integer employeeId;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    @Override
    public String toString() {
        return "EmployeeLoginLog{" +
                "id=" + id +
                ", loginIp='" + loginIp + '\'' +
                ", loginTime=" + loginTime +
                ", employeeId=" + employeeId +
                '}';
    }
}