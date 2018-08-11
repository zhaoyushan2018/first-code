package com.xiaoshan.erp.entity;

import java.io.Serializable;

/**
 * @author 
 */
public class OrderParts implements Serializable {
    /**
     * ID
     */
    private Integer id;

    /**
     * 订单ID
     */
    private Integer orderId;

    /**
     * 备件ID
     */
    private Integer partsId;

    /**
     * 数量
     */
    private Integer num;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getPartsId() {
        return partsId;
    }

    public void setPartsId(Integer partsId) {
        this.partsId = partsId;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return "OrderParts{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", partsId=" + partsId +
                ", num=" + num +
                '}';
    }
}