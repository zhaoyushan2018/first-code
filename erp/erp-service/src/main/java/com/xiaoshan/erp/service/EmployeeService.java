package com.xiaoshan.erp.service;

import com.xiaoshan.erp.entity.Employee;
import com.xiaoshan.erp.exception.ServiceException;

import java.util.List;
import java.util.Map;

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

    /**
     *  移除禁用状态(把员工状态变为可用)
     * @param id 员工id
     */
    void removeStateEmployeeById(Integer id) throws ServiceException;

    /**
     *  验证电话号码 是否存在(除自己以外) 根据电话号码 和id
     * @param id 当前对象id
     * @param employeeTel 需要验证的电话号码
     * @return 已存在 false   可以用true
     */
    Boolean checkUpdateEmployeeTel(Integer id, String employeeTel);

    /**
     * 修改员工属性
     * @param employee 员工对象
     * @param roleIds 角色id 数组
     */
    void saveUpdateEmployee(Employee employee, Integer[] roleIds);


    /**
     * 根据参数 查找对应的员工集合(每个员工对应的角色)
     * @param requestParam 参数
     * @return 员工集合
     */
    List<Employee> fingAllEmployeeListLiftRoleByParam(Map<String, Object> requestParam);
}
