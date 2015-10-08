<%--
  Created by IntelliJ IDEA.
  User: liwang
  Date: 15-10-8
  Time: 上午10:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@page isELIgnored="false"%>
<html>
<head>
    <title>登陆</title>
    <style>.error {
        color: red
    }</style>
</head>
<body>
<div class="error">${error}</div>
<form action="" method="post">
    用户名：<input type="text" name="username"><br>
    密码：<input type="password" name="password"><br>
    自动登陆：<input type="checkbox" name="rememberMe" value="true"><br>
    <input type="submit" value="登陆"><br>
</form>
</body>
</html>
