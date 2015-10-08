<%--
  Created by IntelliJ IDEA.
  User: liwang
  Date: 15-10-8
  Time: 上午10:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
><html>
<head>
    <title></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/css.css">
</head>
<body>

<form method="post">
    <div class="form-group">
        <label for="newPassword">新密码：</label>
        <input type="text" id="newPassword" name="newPassword"/>
    </div>
    <input type="submit" value="确认">
</form>

</body>
</html>
