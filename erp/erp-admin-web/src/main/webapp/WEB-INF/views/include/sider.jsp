<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- 左侧菜单栏 -->
<aside class="main-sidebar">
    <section class="sidebar">
        <!-- 菜单 -->
        <ul class="sidebar-menu">
            <li class="header ${param.menu == 'home' ? 'active' : ''}">系统功能</li>
            <!-- 保养服务 -->
            <li class="treeview">
                <a href="#">
                    <i class="fa fa-share-alt"></i> <span>保养服务</span>
                    <span class="pull-right-container">
                <i class="fa fa-angle-left pull-right"></i>
              </span>
                </a>
                <ul class="treeview-menu">
                    <li><a href="#"><i class="fa fa-circle-o"></i>历史订单查询</a></li>
                    <li><a href="#"><i class="fa fa-circle-o"></i>未完成订单查询</a></li>
                    <li><a href="#"><i class="fa fa-circle-o"></i>新建订单</a></li>
                </ul>
            </li>
            <!-- 维修服务 -->
            <li class="treeview">
                <a href="#">
                    <i class="fa fa-wrench"></i> <span>维修服务</span>
                    <span class="pull-right-container">
                <i class="fa fa-angle-left pull-right"></i>
              </span>
                </a>
                <ul class="treeview-menu">
                    <li><a href="#"><i class="fa fa-circle-o"></i>历史订单查询</a></li>
                    <li><a href="#"><i class="fa fa-circle-o"></i>未完成订单查询</a></li>
                    <li><a href="#"><i class="fa fa-circle-o"></i>新建订单</a></li>
                </ul>
            </li>
            <!-- 库存管理 -->
            <li class="treeview">
                <a href="#">
                    <i class="fa fa-home ${param.menu == 'inventory' ? 'active' : ''}"></i> <span>库存管理</span>
                    <span class="pull-right-container">
                <i class="fa fa-angle-left pull-right"></i>
              </span>
                </a>
                <ul class="treeview-menu">
                    <li><a href="/parts"><i class="fa fa-circle-o"></i>配件管理</a></li>
                    <li><a href="/parts/partsTypeList"><i class="fa fa-circle-o"></i>类型管理</a></li>
                    <li><a href="/parts/new"><i class="fa fa-circle-o"></i>配件入库</a></li>
                    <li><a href="#"><i class="fa fa-circle-o"></i>入库查询</a></li>
                    <li><a href="#"><i class="fa fa-circle-o"></i>出库查询</a></li>
                </ul>
            </li>
            <!-- 结算管理 -->
            <li class="treeview">
                <a href="#">
                    <i class="fa fa-jpy"></i> <span>结算管理</span>
                    <span class="pull-right-container">
                <i class="fa fa-angle-left pull-right"></i>
              </span>
                </a>
                <ul class="treeview-menu">
                    <li><a href="#"><i class="fa fa-circle-o"></i>订单结算</a></li>
                    <li><a href="#"><i class="fa fa-circle-o"></i>优惠券</a></li>
                </ul>
            </li>
            <!-- 统计报表 -->
            <li class="treeview">
                <a href="#">
                    <i class="fa fa-pie-chart"></i> <span>统计报表</span>
                    <span class="pull-right-container">
                <i class="fa fa-angle-left pull-right"></i>
              </span>
                </a>
                <ul class="treeview-menu">
                    <li><a href="#"><i class="fa fa-circle-o"></i>收入统计</a></li>
                </ul>
            </li>


            <li class="header">系统管理</li>
            <!-- 部门员工管理 -->
            <%--<li><a href="#"><i class="fa fa-users"></i> <span>员工管理</span></a></li>--%>
            <%--<li><a href="#"><i class="fa fa-users"></i> <span>角色管理</span></a></li>
            <li><a href="/manage/permission"><i class="fa fa-users"></i> <span>权限管理</span></a></li>--%>

            <li class="treeview">
                <a href="#">
                    <i class="fa fa-users"></i> <span>员工管理</span>
                    <span class="pull-right-container">
                <i class="fa fa-angle-left pull-right"></i>
              </span>
                </a>
                <ul class="treeview-menu">
                    <li><a href="/manage/employee"><i class="fa fa-circle-o"></i>员工列表</a></li>
                    <li><a href="/manage/employee/new"><i class="fa fa-circle-o"></i>新增员工</a></li>
                </ul>
            </li>

            <li class="treeview">
                <a href="#">
                    <i class="fa fa-user-md"></i> <span>角色管理</span>
                    <span class="pull-right-container">
                <i class="fa fa-angle-left pull-right"></i>
              </span>
                </a>
                <ul class="treeview-menu">
                    <li><a href="/manage/role"><i class="fa fa-circle-o"></i>角色列表</a></li>
                    <li><a href="/manage/role/new"><i class="fa fa-circle-o"></i>新增角色</a></li>
                </ul>
            </li>

            <li class="treeview">
                <a href="#">
                    <i class="fa fa-cogs"></i> <span>权限管理</span>
                    <span class="pull-right-container">
                <i class="fa fa-angle-left pull-right"></i>
              </span>
                </a>
                <ul class="treeview-menu">
                    <li><a href="/manage/permission"><i class="fa fa-circle-o"></i>权限列表</a></li>
                    <li><a href="/manage/permission/new"><i class="fa fa-circle-o"></i>新增权限</a></li>
                    <%--<li><a href="/manage/permission/update"><i class="fa fa-circle-o"></i>修改权限</a></li>--%>
                </ul>
            </li>


        </ul>
    </section>
    <!-- /.sidebar -->
</aside>

<!-- =============================================== -->
    