package com.shiro.source.filter;

import com.shiro.source.Constants;
import com.shiro.source.realm.StatelessToken;
import org.apache.shiro.web.filter.AccessControlFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Created by Nikolas on 2015/10/8.
 */
public class StatelessAuthcFilter extends AccessControlFilter {
    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        String clientDigest = servletRequest.getParameter(Constants.PARAM_DIGEST);
        String username = servletRequest.getParameter(Constants.PARAM_USERNAME);
        Map<String, String[]> params = servletRequest.getParameterMap();
        params.remove(Constants.PARAM_DIGEST);
        StatelessToken token = new StatelessToken(username, params, clientDigest);
        try {
            getSubject(servletRequest, servletResponse).login(token);
        } catch (Exception e) {
            e.printStackTrace();
            onLoginFail(servletResponse);
            return false;
        }
        return true;
    }

    private void onLoginFail(ServletResponse servletResponse) throws IOException {
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        httpResponse.getWriter().write("login error");
    }
}
