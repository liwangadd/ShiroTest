<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<html>
<head>
  <title>没有权限</title>
  <style>.error{color:red;}</style>
</head>
<body>

<div class="error">您没有权限[${exception.message}]</div>
</body>
</html>