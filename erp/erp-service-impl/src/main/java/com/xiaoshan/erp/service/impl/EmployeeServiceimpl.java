package com.xiaoshan.erp.service.impl;

import com.xiaoshan.erp.entity.*;
import com.xiaoshan.erp.exception.ServiceException;
import com.xiaoshan.erp.mapper.EmployeeLoginLogMapper;
import com.xiaoshan.erp.mapper.EmployeeMapper;
import com.xiaoshan.erp.mapper.EmployeeRoleMapper;
import com.xiaoshan.erp.service.EmployeeService;
import com.xiaoshan.erp.util.Constant;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author YushanZhao
 * @Date:2018/7/26
 */
@Service
public class EmployeeServiceimpl implements EmployeeService {

    Logger logger = LoggerFactory.getLogger(EmployeeServiceimpl.class);

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private EmployeeRoleMapper employeeRoleMapper;

    @Autowired
    private EmployeeLoginLogMapper employeeLoginLogMapper;

    /**
     * 根据电话号码和密码登录
     *
     * @param loginTel      员工电话号码
     * @param loginPassword 员工登录密码
     */
    @Override
    public Employee login(String loginTel, String loginPassword,String loginIp) throws ServiceException {
        EmployeeExample employeeExample = new EmployeeExample();
        employeeExample.createCriteria().andEmployeeTelEqualTo(loginTel);

        //根据条件查找employee对象列表
        List<Employee> employeeList = employeeMapper.selectByExample(employeeExample);

        Employee employee = null;
        //如果产出来的列表不是null List列表大于0
        if(employeeList != null && employeeList.size() > 0){
            employee = employeeList.get(0);
            //判断密码
            if(employee.getPassword().equals(DigestUtils.md5Hex(loginPassword + Constant.DEFAULT_SALT))){
                //判断状态是否正常 账户状态 0：冻结 1：正常
                if(employee.getState().equals(Employee.EMPLOYEE_STATE_NORMAL)){
                    //记录登录日志
                    EmployeeLoginLog employeeLoginLog = new EmployeeLoginLog();
                    employeeLoginLog.setLoginIp(loginIp);
                    employeeLoginLog.setLoginTime(new Date());
                    employeeLoginLog.setEmployeeId(employee.getId());

                    //记录日志
                    logger.debug("员工:{}-->电话为{}在{}登录了系统...",employee.getEmployeeName(),employee.getEmployeeTel(), new Date());

                    //保存employeeLoginLog 登录日志对象
                    employeeLoginLogMapper.insertSelective(employeeLoginLog);
                    //返回对象
                    return employee;
                }else{
                    throw new ServiceException("登录用的手机号错误或者密码错误...");
                }
            } else {
                throw new ServiceException("登录用的手机号错误或者密码错误...");
            }
        } else {
            throw new ServiceException("登录用的手机号错误或者密码错误...");
        }
    }

    /**
     * 查询全部员工列表
     *
     * @return 员工的List集合
     */
    @Override
    public List<Employee> findAllEmployee() {
        List<Employee> employeeList = employeeMapper.findAllEmployeeLeftRole();

        return employeeList;
    }

    /**
     * 根据employeeTel查找对象
     *
     * @param employeeTel
     * @return
     */
    @Override
    public List<Employee> findEmployeeByTell(String employeeTel) {
        EmployeeExample employeeExample = new EmployeeExample();
        employeeExample.createCriteria().andEmployeeTelEqualTo(employeeTel);
        List<Employee> employeeList = employeeMapper.selectByExample(employeeExample);


        return employeeList;
    }

    /**
     * 保存员工对象, 保存员工和角色的关联关系
     *
     * @param employee 员工对象
     * @param roleIds  该员工的角色id数组
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void saveEmployee(Employee employee, Integer[] roleIds) {
        //保存员工对象
        employeeMapper.insertSelective(employee);

        //保存员工和角色的关联关系表
        for(Integer roleId : roleIds){
            EmployeeRole employeeRole = new EmployeeRole();
            employeeRole.setEmployeeId(employee.getId());
            employeeRole.setRoleId(roleId);

            employeeRoleMapper.insertSelective(employeeRole);
        }

    }

    /**
     * 根据id禁用员工
     *
     * @param id
     */
    @Override
    public void stopStateEmployeeById(Integer id) throws ServiceException{
        //根据id查找员工如果是null,则员工不存在
        Employee employee = employeeMapper.selectByPrimaryKey(id);
        System.out.println("查出来要禁用的对象为： " + employee);

        if(employee == null) {
            throw new ServiceException("参数异常,请稍后重试...");
        }

        //修改状态
        employee.setState(Employee.EMPLOYEE_STATE_FROZEN);
        System.out.println("禁用后的员工对象为： " + employee);

        //重新保存
        employeeMapper.updateByPrimaryKeySelective(employee);
    }

    /**
     * 根据id(employeeId)删除对应的对象(employee)
     *
     * @param id
     */
    @Override
    public void delEmployeeById(Integer id) throws ServiceException{
        Employee employee = employeeMapper.selectByPrimaryKey(id);
        if(employee == null){
            throw new ServiceException("参数异常,请稍后重试...");
        }

        employeeMapper.deleteByPrimaryKey(id);
    }

    /**
     * 根据id查找对象(Employee)
     *
     * @param id
     * @return
     */
    @Override
    public Employee findEmployeeById(Integer id) throws ServiceException{
        Employee employee = employeeMapper.selectByPrimaryKey(id);
        if(employee == null){
            throw new ServiceException("参数异常,请稍后重试...");
        }

        return employee;
    }

    /**
     * 根据电话号码查找对象(Employee)
     *
     * @param userTel 员工登录用的唯一号码
     * @return 员工对象
     */
    @Override
    public Employee findEmployeeByEmployeeTell(String userTel) {

        EmployeeExample employeeExample = new EmployeeExample();
        employeeExample.createCriteria().andEmployeeTelEqualTo(userTel);
        List<Employee> employeeList = employeeMapper.selectByExample(employeeExample);
        if(employeeList != null && employeeList.size() > 0){
            //因为员工电话是唯一的, 所以List集合最多是一个
            Employee employee = employeeList.get(0);
            return employee;
        }
        return null;
    }


}
