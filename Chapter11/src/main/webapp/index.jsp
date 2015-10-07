<%@page contentType = "text/html;charset=utf-8" language="java" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@page isELIgnored="false" %>
<html>
<body>
<shiro:guest>
    欢迎游客访问, <a href="${pageContext.request.contextPath}/login.jsp"%>点击登陆</a><br>
</shiro:guest>
<shiro:user>
    欢迎[<shiro:principal/>]登陆, <a href="${pageContext.request.contextPath}/logout">点击退出</a><br>
</shiro:user>
</body>
</html>