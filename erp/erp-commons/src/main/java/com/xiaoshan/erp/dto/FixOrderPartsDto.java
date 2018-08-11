package com.xiaoshan.erp.dto;

import com.xiaoshan.erp.entity.FixOrderParts;

import java.util.List;

/**
 * @author YushanZhao
 * @Date:2018/8/9
 */
public class FixOrderPartsDto {

    private Integer employeeId;
    private List<FixOrderParts> fixOrderPartsList;

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public List<FixOrderParts> getFixOrderPartsList() {
        return fixOrderPartsList;
    }

    public void setFixOrderPartsList(List<FixOrderParts> fixOrderPartsList) {
        this.fixOrderPartsList = fixOrderPartsList;
    }

    @Override
    public String toString() {
        return "FixOrderPartsDto{" +
                "employeeId=" + employeeId +
                ", fixOrderPartsList=" + fixOrderPartsList +
                '}';
    }
}
