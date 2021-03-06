package com.shiro.source.credentials;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by liwang on 15-10-6.
 */
public class RetryLimitHashedCredentialsMatcher extends HashedCredentialsMatcher {

    private Ehcache passwordRetryCache;

    public RetryLimitHashedCredentialsMatcher(){
        CacheManager cacheManager=new CacheManager(CacheManager.class.getClassLoader().getResource("classpath:ehcache.xml"));
        passwordRetryCache=cacheManager.getCache("passwordCache");
    }

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        String username= (String) token.getPrincipal();
        Element element=passwordRetryCache.get(username);
        if(element==null){
            element=new Element(username, new AtomicInteger(0));
            passwordRetryCache.put(element);
        }
        AtomicInteger retryCount= (AtomicInteger) element.getObjectValue();
        if(retryCount.incrementAndGet() > 5){
            throw new ExcessiveAttemptsException();
        }
        boolean matches=super.doCredentialsMatch(token, info);
        if(matches){
            passwordRetryCache.remove(username);
        }
        return matches;
    }
}
