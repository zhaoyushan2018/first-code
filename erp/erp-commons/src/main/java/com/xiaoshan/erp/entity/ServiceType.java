package com.xiaoshan.erp.entity;

import java.io.Serializable;

/**
 * @author 
 */
public class ServiceType implements Serializable {
    private Integer id;

    /**
     * 服务代号
     */
    private String serviceNo;

    /**
     * 服务名称
     */
    private String serviceName;

    /**
     * 工时
     */
    private String serviceHour;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getServiceNo() {
        return serviceNo;
    }

    public void setServiceNo(String serviceNo) {
        this.serviceNo = serviceNo;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceHour() {
        return serviceHour;
    }

    public void setServiceHour(String serviceHour) {
        this.serviceHour = serviceHour;
    }

    @Override
    public String toString() {
        return "ServiceType{" +
                "id=" + id +
                ", serviceNo='" + serviceNo + '\'' +
                ", serviceName='" + serviceName + '\'' +
                ", serviceHour='" + serviceHour + '\'' +
                '}';
    }
}