package com.shiro.source.jcaptcha;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Created by liwang on 15-10-9.
 */
public class MyFormAuthenticationFilter extends FormAuthenticationFilter {
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        if(request.getAttribute(getFailureKeyAttribute())!=null){
            return true;
        }
        return super.onAccessDenied(request, response);
    }
}
