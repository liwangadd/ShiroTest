<%--
  Created by IntelliJ IDEA.
  User: liwang
  Date: 15-10-7
  Time: 下午8:07
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html;charset=utf-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@page isELIgnored="false" %>
<html>
<head>
    <title>Shiro综合实例</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/layout-default-latest.css">
</head>
<body>
<iframe name="content" class="ui-layout-content"
        src="${pageContext.request.contextPath}/welcome" frameborder="0" scrolling="auto"></iframe>
<div class="ui-layout-north">欢迎[<shiro:principal/>]学习Shiro综合实例, <a
        href="${pageContext.request.contextPath}/logout">退出</a></div>
<div class="ui-layout-south">
    获取源码：<a href="https://github.com/liwangadd/ShiroTest" target="_blank">https://github.com/liwangadd/ShiroTest</a>
</div>
<div class="ui-layout-west">
    功能菜单<br>
    <c:forEach items="${menus}" var="m">
        <a href="${m.url}" target="content">${m.name}</a><br>
    </c:forEach>
</div>
<script src="${pageContext.request.contextPath}/static/js/jquery-1.11.0.min.js"></script>
<script src="${pageContext.request.contentLength}/static/js/jquery.layout-latest.min.js"></script>
<script>
    $(function () {
        $(document).ready(function () {
            $('body').layout({applyDemoStyle: true});
        });
    });
</script>
</body>
</html>
