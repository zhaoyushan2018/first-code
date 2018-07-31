<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>ERP | 账号管理</title>
    <%@include file="../../include/css.jsp"%>
</head>
<body class="hold-transition skin-purple sidebar-mini">
<!-- Site wrapper -->
<div class="wrapper">

    <%@include file="../../include/header.jsp"%>

    <!-- =============================================== -->

    <jsp:include page="../../include/sider.jsp">
        <jsp:param name="menu" value="manage_employee"/>
    </jsp:include>

    <!-- =============================================== -->

    <!-- 右侧内容部分 -->
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1>
                账号管理
            </h1>
        </section>

        <!-- Main content -->
        <section class="content">
            <div class="box no-border">
                <div class="box-body">
                    <form class="form-inline">
                        <input type="text" name="nameMobile" placeholder="账号或手机号码" class="form-control" value="${param.userTel}">
                        <select name="rolesId" class="form-control">
                            <option value="">所有账号</option>
                            <c:forEach items="${rolesList}" var="roles">
                                <option value="${roles.id}" ${param.roleId == role.id ? 'selected' : ''}>${roles.roleName}</option>
                            </c:forEach>
                        </select>
                        <button class="btn btn-default">搜索</button>

                    </form>
                </div>
            </div>
            <div class="box">
                <div class="box-header">
                    <div class="box-tools">

                        <shiro:hasPermission name="employee:add">
                            <a href="/manage/employee/new" class="btn btn-primary btn-sm">
                                <i class="fa fa-plus"></i> 新增账号
                            </a>
                        </shiro:hasPermission>

                    </div>
                </div>
                <div class="box-body">
                    <table class="table">
                        <thead>
                        <tr>
                            <th>账号</th>
                            <th>手机号码</th>
                            <th>角色</th>
                            <th>状态</th>
                            <th>创建时间</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${employeeList}" var="employee">
                            <tr>
                                <td>${employee.employeeName}</td>
                                <td>${employee.employeeTel}</td>
                                <td>
                                    <c:forEach items="${employee.roleList}" var="role">
                                        ${role.roleName}
                                    </c:forEach>
                                </td>
                                <td>
                                        ${employee.state == 1 ? "正常" : "禁用"}
                                </td>
                                <td>
                                    <fmt:formatDate value="${employee.createTime}"/>
                                </td>
                                <td>
                                    <a class="btn btn-warning btn-xs stopEmployee" rel="${employee.id}" href="javascript:;" title="禁用"><i class="fa fa-lock"></i></a>
                                    <a class="btn btn-primary btn-xs" href="/manage/employee/${employee.id}/updateEmployee"><i class="fa fa-edit"></i></a>
                                    <a class="btn btn-danger btn-xs delEmployee" rel="${employee.id}" href="javascript:;" title="删除"><i class="fa fa-trash"></i></a>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
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
    $(function(){

        var message = "${message}";
        if(message){
            layer.msg(message, {icon:2, time:1000});
        }

        $(".stopEmployee").click(function(){
            var id = $(this).attr("rel");

            layer.confirm("确定要禁用该员工吗?", function(){
                $.get("/manage/employee/" + id + "/stopEmployee").done(function(res){
                    if(res.state == "success"){
                        layer.msg("禁用成功...", {icon:2, time:1000}, function(){
                            history.go(0);
                        })
                    } else {
                        layer.msg(res.message);
                    }
                }).error(function(){
                    layer.msg("系统异常,请稍后重试...");
                })
            })
        })

        $(".delEmployee").click(function(){
            var id = $(this).attr("rel");

            layer.confirm("确定要删除该员工吗?", function(){
                $.get("/manage/employee/" + id + "/delEmployee").done(function(res){
                    if(res.state == "success"){
                        layer.msg("删除成功...", {icon:2, time:1000}, function(){
                            history.go(0);
                        })
                    } else {
                        layer.msg(res.message);
                    }
                }).error(function(){
                    layer.msg("系统异常,请稍后重试...");
                })
            })
        })


    })
</script>

</body>
</html>
