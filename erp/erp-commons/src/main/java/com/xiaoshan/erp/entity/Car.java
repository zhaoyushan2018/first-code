package com.xiaoshan.erp.entity;

import java.io.Serializable;

/**
 * @author 
 */
public class Car implements Serializable {
    /**
     * 车辆ID
     */
    private Integer id;

    /**
     * 车辆型号
     */
    private String carType;

    /**
     * 车牌号
     */
    private String licenceNo;

    /**
     * 车辆识别码
     */
    private String carNo;

    /**
     * 客户ID
     */
    private Integer customerId;

    /**
     * 车辆颜色
     */
    private String color;

    /**
     * 车主
     * 一辆车有一个车主,一个车主可以有多辆车
     */
    private Customer customer;

    private static final long serialVersionUID = 1L;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public String getLicenceNo() {
        return licenceNo;
    }

    public void setLicenceNo(String licenceNo) {
        this.licenceNo = licenceNo;
    }

    public String getCarNo() {
        return carNo;
    }

    public void setCarNo(String carNo) {
        this.carNo = carNo;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }


    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", carType='" + carType + '\'' +
                ", licenceNo='" + licenceNo + '\'' +
                ", carNo='" + carNo + '\'' +
                ", customerId=" + customerId +
                ", color='" + color + '\'' +
                ", customer=" + customer +
                '}';
    }
}