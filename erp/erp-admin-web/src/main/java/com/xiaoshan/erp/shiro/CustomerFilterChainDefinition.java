package com.xiaoshan.erp.shiro;

import com.xiaoshan.erp.entity.Permission;
import com.xiaoshan.erp.service.RolePermissionService;
import org.apache.shiro.config.Ini;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author YushanZhao
 * @Date:2018/7/31
 */
public class CustomerFilterChainDefinition {

    @Autowired
    private RolePermissionService rolePermissionService;

    private String filterChainDefinitions;

    private AbstractShiroFilter shiroFilter;

    /**
     * 线程不安全(加锁, 只让一个一个的进 管理员)
     * synchronized 方法上加锁
     * @PostConstructz 创建构造方法时调用
     */
    @PostConstruct
    public synchronized void init(){
        System.out.println("权限初始化中.......................................................................");
        // 清空原有权限
        getFilterChainManager().getFilterChains().clear();
        // 加载新的权限
        load();
    }

    public synchronized void updatePermission(){
        System.out.println("权限更新中..........................................................................");
        // 清空原有权限
        getFilterChainManager().getFilterChains().clear();
         // 加载新的权限
        load();
    }

    public synchronized void load() {
        Ini ini = new Ini();
        ini.load(filterChainDefinitions);

        Ini.Section section = ini.getSection(Ini.DEFAULT_SECTION_NAME);
        List<Permission> permissionList = rolePermissionService.findAllPermission();
        //   /manage/employee/new = perms[employee:add]
        for(Permission permission : permissionList){
            section.put(permission.getUrl(), "perms[" + permission.getPermissionCode() + "]");
        }
        section.put("/**", "user");

        DefaultFilterChainManager defaultFilterChainManager = getFilterChainManager();
        for (Ini.Section.Entry<String, String> entry : section.entrySet()){
            defaultFilterChainManager.createChain(entry.getKey(), entry.getValue());
        }
    }

    public DefaultFilterChainManager getFilterChainManager(){
        //获取FilterChainResolver的实例对象
        PathMatchingFilterChainResolver pathMatchingFilterChainResolver = (PathMatchingFilterChainResolver) this.shiroFilter.getFilterChainResolver();
        //获取FilterChainManager,返回
        DefaultFilterChainManager defaultFilterChainManager = (DefaultFilterChainManager) pathMatchingFilterChainResolver.getFilterChainManager();
        return defaultFilterChainManager;
    }

    public void setFilterChainDefinitions(String filterChainDefinitions){
        this.filterChainDefinitions = filterChainDefinitions;
    }

    public void setShiroFilter(AbstractShiroFilter shiroFilter) {
        this.shiroFilter = shiroFilter;
    }
}
