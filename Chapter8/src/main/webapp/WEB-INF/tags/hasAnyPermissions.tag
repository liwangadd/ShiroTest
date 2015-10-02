<%@ tag import="org.apache.shiro.util.StringUtils" %>
<%@ tag import="org.apache.shiro.subject.Subject" %>
<%@ tag import="org.apache.shiro.SecurityUtils" %>
<%@ attribute name="name" type="java.lang.String" description="权限字符串列表" %>
<%@ attribute name="delimiter" type="java.lang.String" description="权限字符串列表分隔符" %>
<%
    if (!StringUtils.hasText(delimiter)) {
        delimiter = ",";
    }
    if (!StringUtils.hasText(name)) {
%>
<jsp:doBody/>
<%
        return;
    }
    String[] permissions = name.split(delimiter);
    Subject subject = SecurityUtils.getSubject();
    for (String permission : permissions) {
        if (subject.isPermitted(permission)) {
%>
<jsp:doBody/>
<%
        }
    }
%>