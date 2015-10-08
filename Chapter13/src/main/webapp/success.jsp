<%--
  Created by IntelliJ IDEA.
  User: liwang
  Date: 15-10-7
  Time: 下午3:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@page isELIgnored="false" %>
<html>
<head>
    <title></title>
</head>
<body>
<shiro:hasAnyRoles name="admin">
  <shiro:principal/>拥有角色admin
</shiro:hasAnyRoles>
</body>
</html>
