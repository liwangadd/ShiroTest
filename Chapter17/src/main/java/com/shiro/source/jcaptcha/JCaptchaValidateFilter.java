package com.shiro.source.jcaptcha;

import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by liwang on 15-10-9.
 */
public class JCaptchaValidateFilter extends AccessControlFilter {

    private boolean jcaptchaEbabled = true;//是否开启验证码支持

    private String jcaptchaParam = "jcaptchaCode";//前台提交验证码参数名

    private String failureKeyAttribute = "shiroLoginFailure";//验证码验证失败后存储到的属性名

    public void setJcaptchaParam(String jcaptchaParam) {
        this.jcaptchaParam = jcaptchaParam;
    }

    public void setJcaptchaEbabled(boolean jcaptchaEbabled) {
        this.jcaptchaEbabled = jcaptchaEbabled;
    }

    public void setFailureKeyAttribute(String failureKeyAttribute) {
        this.failureKeyAttribute = failureKeyAttribute;
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        servletRequest.setAttribute("jcaptchaEbabled", jcaptchaEbabled);
        HttpServletRequest httpServletRequest = WebUtils.toHttp(servletRequest);

        if (!jcaptchaEbabled || !"post".equalsIgnoreCase(httpServletRequest.getMethod())) {
            return true;
        }

        return JCaptcha.validateResponse(httpServletRequest, httpServletRequest.getParameter(jcaptchaParam));
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        servletRequest.setAttribute(failureKeyAttribute, "jCaptcha.error");
        return true;
    }
}
