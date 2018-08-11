package com.xiaoshan.erp.entity;

import java.io.Serializable;

/**
 * @author 
 */
public class OrderEmployee implements Serializable {

    /**
     * id
     */
    private Integer id;

    /**
     * 员工ID
     */
    private Integer employeeId;

    /**
     * 订单表
     */
    private Integer orderId;

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

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }
}