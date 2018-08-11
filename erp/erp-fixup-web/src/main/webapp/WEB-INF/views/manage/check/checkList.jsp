<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>车管家ERP-首页</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">

    <%@ include file="../../include/css.jsp"%>

</head>
<body class="hold-transition skin-blue sidebar-mini">
<!-- Site wrapper -->
<div class="wrapper">

    <%@ include file="../../include/header.jsp" %>

    <!-- =============================================== -->

    <jsp:include page="../../include/sider.jsp">
        <jsp:param name="menu" value="parts"/>
    </jsp:include>

    <!-- =============================================== -->

    <!-- 右侧内容部分 -->
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1>
                质检部任务领取
            </h1>
        </section>

        <!-- Main content -->
        <section class="content">

            <!-- Default box -->
            <div class="box">
                <div class="box-body">

                    <c:if test="${empty fixOrderList}">
                        <h4>暂无任务</h4>
                    </c:if>

                    <c:forEach items="${fixOrderList}" var="fixOrder">
                        <div class="panel panel-info">
                            <div class="panel-heading">
                                <a href="/check/${fixOrder.orderId}/detail">订单号: ${fixOrder.orderId}</a> - >  车类型: ${fixOrder.carType} - > 服务类型: ${fixOrder.orderType}
                                <c:if test="${fixOrder.state == '4'}">
                                    <button rel="${fixOrder.orderId}" class="btn btn-success btn-sm pull-right receiveBtn">任务领取</button>
                                </c:if>

                            </div>
                            <ul class="list-group">
                                <c:forEach items="${fixOrder.fixOrderPartsList}" var="fixOrderParts">
                                    <li class="list-group-item">${fixOrderParts.partsName} * ${fixOrderParts.partsNum}</li>
                                </c:forEach>
                            </ul>
                        </div>
                    </c:forEach>

                  <%--  <div class="panel panel-info">
                        <!-- Default panel contents -->
                        <div class="panel-heading">订单号：10013 - 大众CC - 保养 <button class="btn btn-success btn-sm pull-right">任务领取</button> </div>
                        <!-- List group -->
                        <ul class="list-group">
                            <li class="list-group-item">机油-嘉实多1L * 2</li>
                            <li class="list-group-item">机油滤芯 * 1</li>
                            <li class="list-group-item">空调滤芯</li>
                        </ul>
                    </div>--%>


                </div>
                <!-- /.box -->

        </section>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->

    <!-- 底部 -->
    <%@ include file="../../include/footer.jsp" %>

</div>
<!-- ./wrapper -->

<%@ include file="../../include/js.jsp" %>

<script>
    $(function(){
        //领取质检任务
        $(".receiveBtn").click(function(){
            var orderId = $(this).attr("rel");
            layer.confirm("确定要领取该质检任务吗?", function(){
                $.get("/check/" + orderId + "/receive", function(resp){
                    if(resp.state == "success"){
                        // 跳转到质检任务详情页面
                        window.location.href = "/check/" + orderId + "/detail";
                    } else {
                        layer.msg(resp.message);
                    }
                })
            })
        })



        $("#pagination").twbsPagination({
            totalPages : 5,
            visiblePages : 7,
            first : '首页',
            last:'末页',
            prev:'上一页',
            next:'下一页',
            href:"#"
        });


    })
</script>
</body>
</html>
