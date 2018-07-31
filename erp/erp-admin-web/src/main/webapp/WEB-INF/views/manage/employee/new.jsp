<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>ERP | 新增账号</title>
    <%@include file="../../include/css.jsp"%>
</head>
<body class="hold-transition skin-purple sidebar-mini">
<!-- Site wrapper -->
<div class="wrapper">

    <%@include file="../../include/header.jsp"%>

    <!-- =============================================== -->

    <jsp:include page="../../include/sider.jsp">
        <jsp:param name="menu" value="manage_account"/>
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
            <div class="box">
                <div class="box-header">
                    <h3 class="box-title">新增账号</h3>
                    <div class="box-tools">
                        <a href="/manage/employee" class="btn btn-primary btn-sm">返回角色列表</a>
                    </div>
                </div>
                <div class="box-body">
                    <form method="post" class="saveForm">
                        <div class="form-group">
                            <label>账号</label>
                            <input type="text" class="form-control" name="employeeName">
                        </div>
                        <div class="form-group">
                            <label>手机号码(用于登录)</label>
                            <input type="text" class="form-control" name="employeeTel">
                        </div>
                        <div class="form-group">
                            <label>密码(默认666666)</label>
                            <input type="password" class="form-control" name="password" value="666666">
                        </div>
                        <div class="form-group">
                            <label>角色</label>
                            <div>
                                <c:forEach items="${roleList}" var="role">
                                    <div class="checkbox-inline">
                                        <input type="checkbox" value="${role.id}" name="roleIds"> ${role.roleName}
                                    </div>
                                </c:forEach>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="box-footer">
                    <button class="btn btn-primary pull-right" id="saveBtn">保存</button>
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
            $(".saveForm").submit();
        });

        $(".saveForm").validate({
            errorElement:'span',
            errorClass:'text-danger',
            rules:{
                employeeName:{
                    required:true
                },
                employeeTel:{
                    required:true,
                    remote:'/manage/employee/check/employeeTel'
                },
                password:{
                    required:true
                },
                roleIds:{
                    required:true
                }
            },
            messages:{
                employeeName:{
                    required:"请输入账号名字..."
                },
                employeeTel:{
                    required:"请输入电话号码用于登录...",
                    remote:'改电话号码已存在,请想好重新输入...'
                },
                password:{
                    required:"请输入密码,注意安全哦..."
                },
                roleIds:{
                    required:"请选择员工职位..."
                }
            }
        })

    });
</script>
</body>
</html>
