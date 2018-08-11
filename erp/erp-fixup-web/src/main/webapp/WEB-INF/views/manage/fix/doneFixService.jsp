<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>车管家ERP-维修服务任务详情表</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <%@ include file="../../include/css.jsp" %>

    <style>
        .td_title {
            font-weight: bold;
        }
        .star {
            font-size: 20px;
            color: #ff7400;
        }
    </style>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<!-- Site wrapper -->
<div class="wrapper">

    <%@ include file="../../include/header.jsp" %>
    <jsp:include page="../../include/sider.jsp">
        <jsp:param name="menu" value="order"/>
    </jsp:include>

    <!-- 右侧内容部分 -->
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <div class="box-header with-border">
                <h1 class="box-title">项目维修</h1>
                <div class="box-tools">
                    <a href="/fix/fixList" class="btn btn-primary btn-sm"><i class="fa fa-arrow-left"></i> 返回列表</a>
                </div>
            </div>
        </section>

        <!-- Main content -->
        <section class="content">

            <!-- Default box -->
            <div class="box">
                <div class="box-body no-padding">

                    <ul class="nav nav-tabs">
                        <li id="undone" class="${type == 'done' ? '' : 'active'}"><a href="/fix/unDoneOrder">未完成任务</a></li>
                        <li id="done" class="${type == 'done' ? 'active' : ''}"><a href="/fix/donelistOrder">已完成任务</a></li>
                    </ul>



                    <c:if test="${empty fixOrderList}">
                        <h4> 您暂无已完成的任务...</h4>
                        <h3> 您暂无已完成的任务...</h3>
                        <h1> 您暂无已完成的任务...</h1>
                    </c:if>

                    <c:forEach items="${fixOrderList}" var="fixOrder">

                        <table class="table">
                            <tbody>
                            <tr>
                                <td class="td_title">车主姓名 :</td><td>${fixOrder.customerName}</td>
                                <td class="td_title">车主电话 :</td><td>${fixOrder.customerTel}</td>
                                <td class="td_title">车牌号码 :</td><td>${fixOrder.carLicence}</td>
                            </tr>
                            <tr>
                                <td class="td_title">车型 :</td><td>${fixOrder.carType}</td>
                                <td class="td_title">颜色 :</td><td>${fixOrder.carColor}</td>
                                <td class="td_title">时间 :</td><td><fmt:formatDate value="${fixOrder.orderTime}" pattern="yyyy-mm-dd"/> </td>
                            </tr>
                            <tr>
                                <td class="td_title">维修员 :</td><td>${fixOrder.fixEmployeeName}</td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                    <!-- /.box-body -->

                </div>
                <!-- /.box -->

                <div class="panel panel-primary">
                    <div class="panel-heading">${fixOrder.orderType} : 所需备件列表</div>

                    <table class="table table-bordered " style="border-width: 2px;">
                        <thead>
                        <tr>
                            <th>编号</th>
                            <th>名称</th>
                            <th>数量</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${fixOrder.fixOrderPartsList}" var="fixOrderParts">
                            <tr>
                                <td>${fixOrderParts.partsNo}</td>
                                <td>${fixOrderParts.partsName}</td>
                                <td>${fixOrderParts.partsNum}</td>
                            </tr>
                        </c:forEach>

                        </tbody>
                    </table>

                </div>

                <%--<c:if test="${fixOrder.fixEmployeeId == currEmployeeId}">
                    <button class="btn btn-success btn-block btn-lg">完成</button>
                </c:if>--%>
            <%--<hr style="border:1px dotted #036" />--%>
           <%-- <hr style="border: 4px solid #ff0000"/>--%>
            <hr style="border:4px dotted #000 "/>
            </c:forEach>

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
        $("#pagination").twbsPagination({
            totalPages : 5,
            visiblePages : 7,
            first : '首页',
            last:'末页',
            prev:'上一页',
            next:'下一页',
            href:"#"
        });

        var locale = {
            "format": 'YYYY-MM-DD',
            "separator": " - ",//
            "applyLabel": "确定",
            "cancelLabel": "取消",
            "fromLabel": "起始时间",
            "toLabel": "结束时间'",
            "customRangeLabel": "自定义",
            "weekLabel": "W",
            "daysOfWeek": ["日", "一", "二", "三", "四", "五", "六"],
            "monthNames": ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
            "firstDay": 1
        };

        var startDate = "";
        var endDate = "";

        if(startDate && endDate) {
            $('#time').val(startDate + " / " + endDate);
        }


        $('#time').daterangepicker({
            autoUpdateInput:false,
            "locale": locale,
            "opens":"right",
            "timePicker":false
        },function(start,end) {
            $("#startTime").val(start.format('YYYY-MM-DD'));
            $("#endTime").val(end.format('YYYY-MM-DD'));

            $('#time').val(start.format('YYYY-MM-DD') + " / " + end.format('YYYY-MM-DD'));
        });
    })
</script>
</body>
</html>
