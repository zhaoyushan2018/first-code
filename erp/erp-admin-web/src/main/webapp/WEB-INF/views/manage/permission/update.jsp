<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>ERP - 系统管理 - 修改</title>
    <%@include file="../../include/css.jsp"%>
</head>
<body class="hold-transition skin-purple sidebar-mini">
<!-- Site wrapper -->
<div class="wrapper">

    <%@include file="../../include/header.jsp"%>

    <!-- =============================================== -->

    <jsp:include page="../../include/sider.jsp">
        <jsp:param name="menu" value="manage_permission"/>
    </jsp:include>

    <!-- =============================================== -->

    <!-- 右侧内容部分 -->
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1>
                权限管理
            </h1>
        </section>

        <!-- Main content -->
        <section class="content">
            <div class="box">
                <div class="box-header">
                    <h3 class="box-title">修改权限</h3>
                    <div class="box-tools">
                        <a href="/manage/permission" class="btn btn-success btn-sm">返回权限列表</a>
                    </div>
                </div>
                <div class="box-body">
                    <form method="post" id="saveForm">
                        <div class="form-group">
                            <label>权限名称</label>
                            <input type="text" name="permissionName" value="${permission.permissionName}" class="form-control">
                        </div>
                        <div class="form-group">
                            <label>权限代号</label>
                            <input type="text" name="permissionCode" value="${permission.permissionCode}" class="form-control">
                        </div>
                        <div class="form-group">
                            <label>资源URL</label>
                            <input type="text" name="url" value="${permission.url}" class="form-control">
                        </div>
                        <div class="form-group">
                            <label>权限类型</label>
                            <%--<input type="text" name="permissionType" value="${permission.permissionType}" class="form-control">--%>

                            <select name="permissionType" class="form-control">
                                <option value="菜单" ${permission.permissionType == "菜单" ? 'selected' : ''}>菜单</option>
                                <option value="按钮" ${permission.permissionType == "按钮" ? 'selected' : ''}>按钮</option>
                            </select>

                        </div>
                        <div class="form-group">
                            <label>父权限</label>
                            <input type="text" name="pid" value="${permission.pid}" class="form-control">

                            <%--<select name="pid" class="form-control">
                                <option value="0">顶级菜单</option>
                                <c:forEach items="${menuPermissionList}" var="allPermission">
                                    <option value="${allPermission.id}" ${permission.id == allPermission.id ? 'selected' : ''}>${allPermission.permissionName}</option>
                                </c:forEach>
                            </select>--%>

                        </div>
                    </form>
                </div>
                <div class="box-footer">
                    <button class="btn pull-right btn-primary" id="saveBtn">保存权限</button>
                </div>
            </div>
        </section>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->
</div>
<!-- ./wrapper -->

<%@include file="../../include/js.jsp"%>
<script>
    $(function () {



        $("#saveBtn").click(function () {
            $("#saveForm").submit();
        });

        $("#saveForm").validate({
            errorElement:'span',
            errorClass:'text-danger',
            rules:{
                permissionName:{
                    required:true,
                    //remote: 加上 远程验证  可用true 不可用false
                    remote:"/manage/permission/${permission.id}/check/permissionNameUpdate"
                },
                permissionCode:{
                    required:true
                },
                url:{
                    required:true
                }
            },
            messages:{
                permissionName:{
                    required:"请输入权限名称...",
                    //不可用提示信息
                    remote:'该权限名称已存在,可以重新换一个...'
                },
                permissionCode:{
                    required:"请输入权限代号..."
                },
                url:{
                    required:"请输入资源URL..."
                }
            }
        })
    })
</script>
</body>
</html>
