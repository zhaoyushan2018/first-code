package com.xiaoshan.erp.service.impl;

import com.xiaoshan.erp.entity.EmployeeLoginLog;
import com.xiaoshan.erp.mapper.EmployeeLoginLogMapper;
import com.xiaoshan.erp.service.EmployeeLoginLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author YushanZhao
 * @Date:2018/7/26
 */
@Service
public class EmployeeLoginLogServiceimpl implements EmployeeLoginLogService {

    @Autowired
    private EmployeeLoginLogMapper employeeLoginLogMapper;

    /**
     * 根据对象 保存登陆日志
     *
     * @param employeeLoginLog 登陆日志
     */
    @Override
    public void saveLoginLog(EmployeeLoginLog employeeLoginLog) {
        employeeLoginLogMapper.insertSelective(employeeLoginLog);
    }
}
