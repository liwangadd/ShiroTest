package com.shiro.test.filter;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.web.filter.PathMatchingFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Nikolas on 2015/9/25.
 */
public class FormLoginFilter extends PathMatchingFilter {
    @Override
    protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        if (SecurityUtils.getSubject().isAuthenticated()) {
            return true;//ÒÑ¾­µÇÂ¼¹ý
        }
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        if (isLoginRequest(req)) {
            if("post".equals(((HttpServletRequest) request).getMethod())){
                boolean loginSuccess=login(req);
                if(loginSuccess){
                    return redirectToSuccessUrl(req,resp);
                }
            }
            return true;
        }else{
            saveRequestAndRedirectToLogin(req,resp);
            return false;
        }
    }

    public boolean redirectToSuccessUrl(HttpServletRequest request, HttpServletResponse response) throws IOException {
        WebUtils.redirectToSavedRequest(request, response, "/");
        return true;
    }

    public void saveRequestAndRedirectToLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        WebUtils.saveRequest(request);
        WebUtils.issueRedirect(request, response, "/login.jsp");
    }

    public boolean login(HttpServletRequest request) {
        String username = request.getParameter("username").toString();
        String password = request.getParameter("password").toString();
        try {
            SecurityUtils.getSubject().login(new UsernamePasswordToken(username, password));
        } catch (Exception e) {
            request.setAttribute("shitoLoginFailure", e.getClass());
            return false;
        }
        return true;
    }

    public boolean isLoginRequest(HttpServletRequest request) {
        return pathsMatch("/login.jsp", WebUtils.getPathWithinApplication(request));
    }
}
