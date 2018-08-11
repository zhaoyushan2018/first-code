<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>车管家ERP</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">

    <!-- Bootstrap 3.3.6 -->
    <link rel="stylesheet" href="/static/bootstrap/css/bootstrap.min.css">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="/static/plugins/font-awesome/css/font-awesome.min.css">
    <!-- Theme style -->
    <link rel="stylesheet" href="/static/dist/css/AdminLTE.min.css">

    <!-- AdminLTE Skins. Choose a skin from the css/skins
         folder instead of downloading all of them to reduce the load. -->
    <link rel="stylesheet" href="/static/dist/css/skins/_all-skins.min.css">
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://cdn.bootcss.com/html5shiv/r29/html5.min.js"></script>
    <script src="https://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body class="hold-transition login-page">
<div class="login-box">
    <div class="login-logo">
        <a href="../../index2.html"><b>汽车售后服务平台</b></a>
    </div>
    <!-- /.login-logo -->

    <div class="login-box-body">
        <form action="/" method="post" id="loginForm">
            <c:if test="${not empty message}">
                <div class="alert-danger">${message}</div>
            </c:if>
            <div class="form-group has-feedback">
                <input type="phone" class="form-control" name="loginTel" placeholder="请输入登录可用的手机号码...">
                <span class="glyphicon glyphicon-phone form-control-feedback"></span>
            </div>
            <div class="form-group has-feedback">
                <input type="password" class="form-control" name="loginPassword" placeholder="请输入密码...">
                <span class="glyphicon glyphicon-lock form-control-feedback"></span>
            </div>
            <div class="row">
                <div class="col-xs-8">

                    <div class="checkbox">
                        <label>
                            <input type="checkbox" name="remember" value="remember"> 记住账号
                        </label>
                    </div>

                    <%--<div class="col-sm-offset-1 col-sm-11">
                        <input type="checkbox" name="remember" id="remember" value="remember"
                        &lt;%&ndash;<c:if test="${not empty adminName}">
                               checked
                        </c:if>&ndash;%&gt;
                               style="margin-right:4px;">
                            <label id="rememberme" for="remember">记住账号</label>
                    </div>--%>
                    <%--<div class="checkbox icheck">
                        <label>
                            <a href="#">忘记密码</a><br>
                        </label>
                    </div>--%>
                </div>
                <!-- /.col -->
                <!-- /.col -->
            </div>
        </form>

       <%-- <div class="col-offset-8 col-xs-4">--%>
            <button type="button" class="btn btn-primary btn-block btn-flat" id="loginBtn">登录</button>
        <%--</div>--%>


    </div>
    <!-- /.login-box-body -->
</div>
<!-- /.login-box -->

<!-- jQuery 2.2.3 -->
<script src="/static/plugins/jQuery/jquery-2.2.3.min.js"></script>
<!-- Bootstrap 3.3.6 -->
<script src="/static/bootstrap/js/bootstrap.min.js"></script>
<%-- validate验证 --%>
<script src="/static/dist/js/jquery.validate.min.js"></script>

    <script>
        $(function(){

            $("#loginBtn").click(function(){
                $("#loginForm").submit();
            })

            $("#loginForm").validate({
                errorElement:'span',
                errorClass:'text-danger',
                rules:{
                    loginTel:{
                        required:true
                    },
                    loginPassword:{
                        required:true
                    }
                },
                messages:{
                    loginTel:{
                        required:'请输入登录可用的手机号码...'
                    },
                    loginPassword:{
                        required:'请输入登录密码...'
                    }
                }
            })


        })
    </script>

</body>
</html>
