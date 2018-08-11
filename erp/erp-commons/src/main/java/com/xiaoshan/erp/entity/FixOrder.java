package com.xiaoshan.erp.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author 
 */
public class FixOrder implements Serializable {
    /**
     * 订单号
     */
    private Integer orderId;

    /**
     * 订单类型
     */
    private String orderType;

    /**
     * 订单日期
     */
    private Date orderTime;

    /**
     * 订单状态：2：已下发 3：维修中 4：维修完成 5：质检中 6：结算中
     */
    private String state;

    /**
     * 订单金额
     */
    private BigDecimal orderMoney;

    /**
     * 订单工时
     */
    private String orderServiceHour;

    /**
     * 订单工时费
     */
    private BigDecimal orderServiceHourFee;

    /**
     * 订单配件费用
     */
    private BigDecimal orderPartsFee;

    /**
     * 车类型
     */
    private String carType;

    /**
     * 车颜色
     */
    private String carColor;

    /**
     * 车牌号
     */
    private String carLicence;

    /**
     * 车主名称
     */
    private String customerName;

    /**
     * 车主电话
     */
    private String customerTel;

    /**
     * 维修人员id
     */
    private Integer fixEmployeeId;

    /**
     *  质检人员Id
     */
    private Integer checkEmployeeId;

    /**
     *  维修人员名字
     */
    private String fixEmployeeName;

    /**
     *  质检人员名字
     */
    private String checkEmployeeName;

    /**
     *  维修订单所需要的配件集合
     */
    private List<FixOrderParts> fixOrderPartsList;

    private static final long serialVersionUID = 1L;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public BigDecimal getOrderMoney() {
        return orderMoney;
    }

    public void setOrderMoney(BigDecimal orderMoney) {
        this.orderMoney = orderMoney;
    }

    public String getOrderServiceHour() {
        return orderServiceHour;
    }

    public void setOrderServiceHour(String orderServiceHour) {
        this.orderServiceHour = orderServiceHour;
    }

    public BigDecimal getOrderServiceHourFee() {
        return orderServiceHourFee;
    }

    public void setOrderServiceHourFee(BigDecimal orderServiceHourFee) {
        this.orderServiceHourFee = orderServiceHourFee;
    }

    public BigDecimal getOrderPartsFee() {
        return orderPartsFee;
    }

    public void setOrderPartsFee(BigDecimal orderPartsFee) {
        this.orderPartsFee = orderPartsFee;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public String getCarColor() {
        return carColor;
    }

    public void setCarColor(String carColor) {
        this.carColor = carColor;
    }

    public String getCarLicence() {
        return carLicence;
    }

    public void setCarLicence(String carLicence) {
        this.carLicence = carLicence;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerTel() {
        return customerTel;
    }

    public void setCustomerTel(String customerTel) {
        this.customerTel = customerTel;
    }

    public Integer getFixEmployeeId() {
        return fixEmployeeId;
    }

    public void setFixEmployeeId(Integer fixEmployeeId) {
        this.fixEmployeeId = fixEmployeeId;
    }

    public Integer getCheckEmployeeId() {
        return checkEmployeeId;
    }

    public void setCheckEmployeeId(Integer checkEmployeeId) {
        this.checkEmployeeId = checkEmployeeId;
    }

    public String getFixEmployeeName() {
        return fixEmployeeName;
    }

    public void setFixEmployeeName(String fixEmployeeName) {
        this.fixEmployeeName = fixEmployeeName;
    }

    public String getCheckEmployeeName() {
        return checkEmployeeName;
    }

    public void setCheckEmployeeName(String checkEmployeeName) {
        this.checkEmployeeName = checkEmployeeName;
    }

    public List<FixOrderParts> getFixOrderPartsList() {
        return fixOrderPartsList;
    }

    public void setFixOrderPartsList(List<FixOrderParts> fixOrderPartsList) {
        this.fixOrderPartsList = fixOrderPartsList;
    }

    @Override
    public String toString() {
        return "FixOrder{" +
                "orderId=" + orderId +
                ", orderType='" + orderType + '\'' +
                ", orderTime=" + orderTime +
                ", state='" + state + '\'' +
                ", orderMoney=" + orderMoney +
                ", orderServiceHour='" + orderServiceHour + '\'' +
                ", orderServiceHourFee=" + orderServiceHourFee +
                ", orderPartsFee=" + orderPartsFee +
                ", carType='" + carType + '\'' +
                ", carColor='" + carColor + '\'' +
                ", carLicence='" + carLicence + '\'' +
                ", customerName='" + customerName + '\'' +
                ", customerTel='" + customerTel + '\'' +
                ", fixEmployeeId=" + fixEmployeeId +
                ", checkEmployeeId=" + checkEmployeeId +
                ", fixEmployeeName='" + fixEmployeeName + '\'' +
                ", checkEmployeeName='" + checkEmployeeName + '\'' +
                ", fixOrderPartsList=" + fixOrderPartsList +
                '}';
    }
}