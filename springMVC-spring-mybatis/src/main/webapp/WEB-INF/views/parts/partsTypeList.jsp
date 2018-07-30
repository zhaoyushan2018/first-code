<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>车管家ERP-配件类型管理</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">

    <%@ include file="../include/css.jsp" %>

</head>
<body class="hold-transition skin-blue sidebar-mini">
<!-- Site wrapper -->
<div class="wrapper">

    <%@ include file="../include/header.jsp" %>
    <jsp:include page="../include/sider.jsp">
        <jsp:param name="menu" value="parts"/>
    </jsp:include>

    <!-- =============================================== -->

    <!-- 右侧内容部分 -->
    <div class="content-wrapper">

        <!-- Main content -->
        <section class="content">
            <!-- Default box -->
            <div class="box">
                <div class="box-header with-border">
                    <h3 class="box-title">配件类型管理</h3>
                    <div class="box-tools pull-right">
                        <%--<button type="button" class="btn btn-box-tool"  title="Collapse">
                            <i class="fa fa-plus"></i> 添加类型</button>--%>

                        <!-- 按钮触发模态框 -->
                        <button class="btn btn-primary btn-lg" data-toggle="modal" data-target="#myModal">
                            新增
                        </button>
                        <!-- 模态框（Modal） -->
                        <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                                            &times;
                                        </button>
                                        <h4 class="modal-title" id="addType">
                                            请输入要新增配件类型
                                        </h4>
                                    </div>
                                    <%--<div class="modal-body">
                                        在这里添加一些文本
                                    </div>--%>
                                    <input type="text" name="addTypeName" class="form-control" style="border: #00a157 " placeholder="要在这里输入哦...">


                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                                        </button>
                                        <button type="button" class="btn btn-primary" id="addBtn">
                                            提交
                                        </button>
                                    </div>
                                </div><!-- /.modal-content -->
                            </div><!-- /.modal -->
                       <%-- <form role="form">
                            <div class="form-group">
                                <label for="name">标签</label>
                                <input type="text" class="form-control" placeholder="文本输入">
                            </div>
                        </form>--%>

                    </div>
                </div>
                <div class="box-body">
                    <table class="table">
                        <thead>
                        <tr>
                            <th>类型ID</th>
                            <th>类型名字</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>

                        <c:forEach items="${typePage.list}" var="type">
                            <tr>
                                <td>${type.id}</td>
                                <td>${type.typeName}</td>
                                <td>
                                    <a href="javascript:;" class="del" ref="${type.id}">删除</a>
                                    <a href="javascript:;">

                                        <!-- 按钮触发模态框 -->
                                        <button data-toggle="modal" data-target="#myModal1">
                                            编辑
                                        </button>
                                        <!-- 模态框（Modal） -->
                                        <div class="modal fade" id="myModal1" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                                            <div class="modal-dialog">
                                                <div class="modal-content">
                                                    <div class="modal-header">
                                                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                                                            &times;
                                                        </button>
                                                        <h4 class="modal-title" id="myModalLabel">
                                                            请输入要修改的配件类型
                                                        </h4>
                                                    </div>
                                                        <%--<div class="modal-body">
                                                            在这里添加一些文本
                                                        </div>--%>
                                                    <input type="text" class="form-control" style="border: #00a157 " placeholder="要在这里输入哦..." >


                                                    <div class="modal-footer">
                                                        <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                                                        </button>
                                                        <button type="button" class="btn btn-primary">
                                                            提交
                                                        </button>
                                                    </div>
                                                </div><!-- /.modal-content -->
                                            </div><!-- /.modal -->


                                    </a>
                                </td>
                            </tr>
                        </c:forEach>

                        </tbody>
                    </table>

                    <ul id="pagination" class="pagination pull-right"></ul>

                </div>
            </div>
            <!-- /.box -->
        </section>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->

    <!-- 底部 -->
    <%@ include file="../include/footer.jsp" %>

</div>

<%@ include file="../include/js.jsp" %>

<script>
    $(function(){

        $("#addBtn").click(function(){

        })


   /* ------------------------------------------------------------------------------------------------------------------- */
        var message = "${message}";
        if(message){
            layer.msg(message);
        }

        $(".del").click(function(){
            var id = $(this).attr("ref");
            layer.confirm("确定要删除吗?", function(){
                window.location.href = "/type/' + id + '/del";
            })
        })



        $("#pagination").twbsPagination({
            totalPages : ${typePage.pages},
            visiblePages : 5,
            first : '首页',
            last:'末页',
            prev:'上一页',
            next:'下一页',
            //从哪来回哪去,不用写路径.
            // 在下一页 回显 筛选条件 后面或前面跟数据   注意空格(有影响)
            href:"?p={{number}}"
        });

    });
</script>
</body>
</html>
