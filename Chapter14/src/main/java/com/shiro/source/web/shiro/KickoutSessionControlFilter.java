package com.shiro.source.web.shiro;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;
import java.util.Deque;
import java.util.LinkedList;

/**
 * Created by liwang on 15-10-8.
 */
public class KickoutSessionControlFilter extends AccessControlFilter {

    private String kickoutUrl;//提出后到的地址

    private boolean kickoutAfter = false;//踢出之前登陆的/之后登陆的用户，默认踢出之前登陆的用户

    private int maxSession = 1;//同一个账号最大会话数

    private SessionManager sessionManager;

    private Cache<String, Deque<Serializable>> cache;

    public void setKickoutAfter(boolean kickoutAfter) {
        this.kickoutAfter = kickoutAfter;
    }

    public void setKickoutUrl(String kickoutUrl) {
        this.kickoutUrl = kickoutUrl;
    }

    public void setMaxSession(int maxSession) {
        this.maxSession = maxSession;
    }

    public void setSessionManager(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    public void setCacheManager(CacheManager cacheManager) {
        this.cache = cacheManager.getCache("shiro-kickout-session");
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        Subject subject = getSubject(servletRequest, servletResponse);
        if (!subject.isAuthenticated() && !subject.isRemembered()) {
            return true;
        }
        Session session = subject.getSession();
        String username = (String) subject.getPrincipal();
        Serializable sessionId = session.getId();

        Deque<Serializable> deque = cache.get(username);
        if (deque == null) {
            deque = new LinkedList<>();
            cache.put(username, deque);
        }
        if (!deque.contains(sessionId) && session.getAttribute("kickout") == null) {
            deque.push(sessionId);
        }
        while (deque.size() > maxSession) {
            Serializable kickoutSessionId = null;
            if (kickoutAfter) {
                kickoutSessionId = deque.removeFirst();
            } else {
                kickoutSessionId = deque.removeLast();
            }
            try {
                Session kickoutSession = sessionManager.getSession(new DefaultSessionKey(kickoutSessionId));
                if (kickoutSession != null) {
                    kickoutSession.setAttribute("kickout", true);
                }
            } catch (Exception e) {

            }
        }
        if (session.getAttribute("kickout") != null) {
            subject.logout();
            saveRequest(servletRequest);
            WebUtils.issueRedirect(servletRequest, servletResponse, kickoutUrl);
            return false;
        }
        return true;
    }
}
