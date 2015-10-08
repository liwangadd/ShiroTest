<%--
  Created by IntelliJ IDEA.
  User: liwang
  Date: 15-10-7
  Time: 下午3:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>没有权限</title>
  <style>.error{color:red}</style>
</head>
<body>
<div class="error">您没有权限[${exception.message}]</div>
</body>
</html>
