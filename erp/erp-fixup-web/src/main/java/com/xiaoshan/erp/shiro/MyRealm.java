package com.xiaoshan.erp.shiro;

import com.xiaoshan.erp.entity.Employee;
import com.xiaoshan.erp.entity.EmployeeLoginLog;
import com.xiaoshan.erp.entity.Permission;
import com.xiaoshan.erp.entity.Role;
import com.xiaoshan.erp.service.EmployeeLoginLogService;
import com.xiaoshan.erp.service.EmployeeService;
import com.xiaoshan.erp.service.RolePermissionService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * @author YushanZhao
 * @Date:2018/7/30
 */
public class MyRealm extends AuthorizingRealm {

    Logger logger = LoggerFactory.getLogger(MyRealm.class);

    @Autowired
    private RolePermissionService rolePermissionService;

    @Autowired
    private EmployeeLoginLogService employeeLoginLogService;

    @Autowired
    private EmployeeService employeeService;

    /**
     * 判断权限角色,授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取当前登陆的对象
        Employee employee = (Employee) principalCollection.getPrimaryPrincipal();

        //获取当前登录对象拥有的角色
        List<Role> roleList = rolePermissionService.findRoleListByEmployeeId(employee.getId());
        //获取当前对象拥有的权限
        List<Permission> permissionList = new ArrayList<>();
        for(Role role : roleList){
            List<Permission> permissions = rolePermissionService.findAllPermissionListByRoleId(role.getId());
            permissionList.addAll(permissions);
        }

        Set<String> roleCodeSet = new HashSet<>();
        for(Role role : roleList){
            roleCodeSet.add(role.getRoleCode());
        }

        Set<String> permissionCodeSet = new HashSet<>();
        for(Permission per : permissionList){
            permissionCodeSet.add(per.getPermissionCode());
        }

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        //当前用户拥有的角色(code)[角色编号]
        simpleAuthorizationInfo.setRoles(roleCodeSet);
        //当前用户拥有的权限(code)[权限编号]
        simpleAuthorizationInfo.setStringPermissions(permissionCodeSet);

        return simpleAuthorizationInfo;
    }

    /**
     * 判断登录
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken)authenticationToken;

        String userTel = usernamePasswordToken.getUsername();
        System.out.println("MyRealm: " + userTel);

        Employee employee = employeeService.findEmployeeByEmployeeTell(userTel);

        if(employee == null){
            throw new UnknownAccountException("帐户名或者密码错误");
        }

        if(employee.getState().equals(Employee.EMPLOYEE_STATE_FROZEN)){
            throw new LockedAccountException("帐户已被冻结,请联系管理员...");
        }

        //上面两个验证都通过的话则,账号存在 正常, 可以登录(shiro自动验证密码)
        //获取登录ip
        String logIp = usernamePasswordToken.getHost();
        //记录登陆日志
        EmployeeLoginLog employeeLoginLog = new EmployeeLoginLog();
        employeeLoginLog.setLoginIp(logIp);
        employeeLoginLog.setEmployeeId(employee.getId());
        employeeLoginLog.setLoginTime(new Date());
        //保存登录日志 (是否登录成功都会记录登录日志)
        employeeLoginLogService.saveLoginLog(employeeLoginLog);

        logger.info("{}-->电话为:{} 在 {} 登录了系统", employee.getEmployeeName(),employee.getEmployeeTel(),new Date());

        /**
         * 1.数据库查找出来的对象,或者是登录名字(也就是登录用的手机号码)
         * 2.数据库查找出来的密码...   3.现在登陆的账号名(现在登录的手机号码)
         */
        return new SimpleAuthenticationInfo(employee, employee.getPassword(), getName());
    }







}
