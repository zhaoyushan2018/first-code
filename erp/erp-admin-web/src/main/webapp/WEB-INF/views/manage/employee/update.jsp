<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>ERP | 修改账号</title>
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
                    <h3 class="box-title">修改账号</h3>
                    <div class="box-tools">
                        <a href="/manage/employee" class="btn btn-primary btn-sm">返回角色列表</a>
                    </div>
                </div>
                <div class="box-body">
                    <form method="post" class="saveForm">
                        <div class="form-group">
                            <label>账号</label>
                            <input type="text" class="form-control" value="${employee.employeeName}" name="employeeName">
                        </div>
                        <div class="form-group">
                            <label>手机号码(用于登录)</label>
                            <input type="text" class="form-control" value="${employee.employeeTel}" name="employeeTel">
                        </div>
                        <div class="form-group">
                            <label>角色</label>
                            <div>
                                <c:forEach items="${allRoleList}" var="role">
                                    <c:set var="flag" value="false"/>
                                    <c:forEach items="${nowRoleList}" var="empRole">
                                        <c:if test="${role.id == empRole.id}">
                                            <c:set var="flag" value="true"/>
                                        </c:if>
                                    </c:forEach>

                                    <div class="checkbox-inline">
                                        <input type="checkbox" value="${role.id}" name="roleIds" ${flag ? 'checked': ''}> ${role.roleName}
                                    </div>
                                </c:forEach>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="box-footer">
                    <button class="btn btn-primary pull-right" id="saveBtn">保存修改</button>
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
                    remote:'/manage/employee/${employee.id}/check/updateEmployeeTel'
                },
                roleIds:{
                    required:true
                }
            },
            messages:{
                employeeName:{
                    required:"请输入员工名字..."
                },
                employeeTel:{
                    required:"请输入可用于登录的手机号码...",
                    remote:"该手机号码已经存在,请检查无误后,重新输入..."
                },
                roleIds:{
                    required:"请至少选择一个员工职位哦..."
                }
            }
        })

    });
</script>
</body>
</html>
