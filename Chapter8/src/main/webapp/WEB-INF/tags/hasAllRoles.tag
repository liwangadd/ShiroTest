<%@ tag import="org.apache.shiro.util.StringUtils" %>
<%@ tag import="org.apache.shiro.SecurityUtils" %>
<%@ tag import="java.util.Arrays" %>
<%@ attribute name="name" type="java.lang.String" description="��ɫ�б�" %>
<%@ attribute name="delimiter" type="java.lang.String" description="��ɫ�б�ָ���" %>
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