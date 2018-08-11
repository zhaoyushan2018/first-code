<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- 左侧菜单栏 -->
<aside class="main-sidebar">
    <section class="sidebar">
        <!-- 菜单 -->
        <ul class="sidebar-menu">
            <li class="header ${param.menu == 'home' ? 'active' : ''}">车辆服务</li>
            <!-- 保养服务 -->
            <li class="treeview">
                <a href="#">
                    <i class="fa fa-share-alt"></i> <span>车辆服务</span>
                    <span class="pull-right-container">
                <i class="fa fa-angle-left pull-right"></i>
              </span>
                </a>
                <ul class="treeview-menu">
                    <li><a href="/order/new"><i class="fa fa-circle-o"></i>新增订单</a></li>
                    <li><a href="/order/done/list"><i class="fa fa-circle-o"></i>已完成订单查询</a></li>
                    <li><a href="/order/undone/unlist"><i class="fa fa-circle-o"></i>未完成订单查询</a></li>
                    <li><a href="#"><i class="fa fa-circle-o"></i>历史订单查询</a></li>
                </ul>
            </li>

        </ul>
    </section>
    <!-- /.sidebar -->
</aside>

<!-- =============================================== -->
    