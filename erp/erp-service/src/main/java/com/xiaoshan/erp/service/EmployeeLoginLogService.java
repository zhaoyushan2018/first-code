package com.xiaoshan.erp.service;

import com.xiaoshan.erp.entity.EmployeeLoginLog;

/**
 * @author YushanZhao
 * @Date:2018/7/26
 */
public interface EmployeeLoginLogService {
    /**
     * 根据对象 保存登陆日志
     * @param employeeLoginLog 登陆日志
     */
    void saveLoginLog(EmployeeLoginLog employeeLoginLog);
}
