<%--
  Created by IntelliJ IDEA.
  User: YushanZhao
  Date: 2018/7/23
  Time: 10:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>文件上传</title>
</head>
<body>

    <h1>文件上传</h1>

    <c:if test="${not empty message}">
        <span style="color:red">${message}</span>
    </c:if>

    <form action="" method="post"enctype="multipart/form-data">
        <input type="text" name="fileName"> <br>
        <input type="file" name="file"> <br>
        <button>save</button>
    </form>

</body>
</html>
