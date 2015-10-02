<%@ tag import="org.apache.shiro.util.StringUtils" %>
<%@ tag import="org.apache.shiro.SecurityUtils" %>
<%@ tag import="java.util.Arrays" %>
<%@ attribute name="name" type="java.lang.String" description="角色列表" %>
<%@ attribute name="delimiter" type="java.lang.String" description="角色列表分隔符" %>
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
    String[] roles = name.split(delimiter);
    if (!SecurityUtils.getSubject().hasAllRoles(Arrays.asList(roles))) {
        return;
    } else {
%>
<jsp:doBody/>
<%
    }
%>