<%@ tag import="org.apache.shiro.util.StringUtils" %>
<%@ tag import="org.apache.shiro.SecurityUtils" %>
<%@ attribute name="name" type="java.lang.String" description="Ȩ���ַ����б�" %>
<%@ attribute name="delimiter" type="java.lang.String" description="Ȩ���ַ����б�ָ���" %>
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
    if (SecurityUtils.getSubject().isPermittedAll(roles)) {
        return;
    } else {
%>
<jsp:doBody/>
<%
    }
%>