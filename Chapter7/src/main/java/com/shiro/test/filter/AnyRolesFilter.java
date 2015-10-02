package com.shiro.test.filter;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.StringUtils;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Nikolas on 2015/9/25.
 */
public class AnyRolesFilter extends AccessControlFilter {

    private String unauthorizedUrl = "/unauthorized.jsp";

    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        String[] roles = (String[]) o;
        if (roles == null) {
            return true;
        }
        for (String role : roles) {
            if (getSubject(servletRequest, servletResponse).hasRole(role)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        Subject subject = getSubject(servletRequest, servletResponse);
        if (subject.getPrincipal() == null) {
            saveRequest(servletRequest);
            WebUtils.issueRedirect(servletRequest, servletResponse, "/login.jsp");
        } else {
            if (StringUtils.hasText(unauthorizedUrl)) {
                WebUtils.issueRedirect(servletRequest, servletResponse, unauthorizedUrl);
            } else {
                WebUtils.toHttp(servletResponse).sendError(HttpServletResponse.SC_UNAUTHORIZED);
            }
        }
        return false;
    }
}
