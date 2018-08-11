package com.xiaoshan.erp.dto;

import com.xiaoshan.erp.entity.Order;
import com.xiaoshan.erp.entity.Parts;
import com.xiaoshan.erp.entity.ServiceType;

import java.util.List;

/**
 * dto数据传输对象
 * @author YushanZhao
 * @Date:2018/8/9
 */
public class OrderInfoDto {

    /**
     * 订单详细信息
     */
    private Order order;

    /**
     * 服务类型
     */
    private ServiceType serviceType;

    /**
     * 订单配件信息及数量
     */
    private List<Parts> partsList;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public ServiceType getServiceType() {
        return serviceType;
    }

    public void setServiceType(ServiceType serviceType) {
        this.serviceType = serviceType;
    }

    public List<Parts> getPartsList() {
        return partsList;
    }

    public void setPartsList(List<Parts> partsList) {
        this.partsList = partsList;
    }

    @Override
    public String toString() {
        return "OrderInfoDto{" +
                "order=" + order +
                ", serviceType=" + serviceType +
                ", partsList=" + partsList +
                '}';
    }
}
