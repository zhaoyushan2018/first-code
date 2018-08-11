<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- 左侧菜单栏 -->
<aside class="main-sidebar">
    <section class="sidebar">
        <!-- 菜单 -->
        <ul class="sidebar-menu">
            <li class="header ${param.menu == 'home' ? 'active' : ''}">车辆保养维修服务</li>
            <!-- 保养维修服务 -->
            <li class="treeview">
                <a href="#">
                    <i class="fa fa-wrench"></i> <span>车辆维修服务</span>
                    <span class="pull-right-container">
                        <i class="fa fa-angle-left pull-right"></i>
                    </span>
                </a>
                <ul class="treeview-menu">
                    <li><a href="/fix/fixList"><i class="fa fa-circle-o"></i>维修任务大厅</a></li>
                    <li><a href="/fix/unDoneOrder"><i class="fa fa-circle-o"></i>已接维修任务</a></li>
                </ul>
            </li>

            <!-- 维修之间服务 -->
            <li class="treeview">
                <a href="#">
                    <i class="fa fa-share-alt"></i> <span>车辆质检服务</span>
                    <span class="pull-right-container">
                        <i class="fa fa-angle-left pull-right"></i>
                    </span>
                </a>
                <ul class="treeview-menu">
                    <li><a href="/check/checkList"><i class="fa fa-circle-o"></i>质检任务大厅</a></li>
                    <li><a href="/check/checkService"><i class="fa fa-circle-o"></i>已接质检任务</a></li>
                </ul>
            </li>

        </ul>
    </section>
    <!-- /.sidebar -->
</aside>

<!-- =============================================== -->
    