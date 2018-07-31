package com.xiaoshan.erp.service;

import com.xiaoshan.erp.entity.Employee;
import com.xiaoshan.erp.exception.ServiceException;

import java.util.List;

/**
 * @author YushanZhao
 * @Date:2018/7/26
 */
public interface EmployeeService {

    /**
     * 根据电话号码和密码登录
     * @param loginTel 员工电话号码
     * @param loginPassword 员工登录密码
     */
    Employee login(String loginTel, String loginPassword, String loginIp) throws ServiceException;

    /**
     * 查询全部员工列表
     * @return 员工的List集合
     */
    List<Employee> findAllEmployee();

    /**
     *  根据employeeTel查找对象
     * @param employeeTel
     * @return List集合
     */
    List<Employee> findEmployeeByTell(String employeeTel);

    /**
     * 保存员工对象, 保存员工和角色的关联关系
     * @param employee 员工对象
     * @param roleIds 该员工的角色id数组
     */
    void saveEmployee(Employee employee, Integer[] roleIds);

    /**
     * 根据id禁用员工
     * @param id
     */
    void stopStateEmployeeById(Integer id) throws ServiceException;

    /**
     * 根据id(employeeId)删除对应的对象(employee)
     * @param id
     */
    void delEmployeeById(Integer id) throws ServiceException;

    /**
     * 根据id查找对象(Employee)
     * @param id
     * @return
     */
    Employee findEmployeeById(Integer id) throws ServiceException;

    /**
     *  根据电话号码查找对象(Employee)
     * @param userTel 员工登录用的唯一号码
     * @return 员工对象
     */
    Employee findEmployeeByEmployeeTell(String userTel);
}
