package com.shiro.source.scheduler;

import com.shiro.source.JdbcTemplateUtils;
import com.shiro.source.SerializableUtils;
import net.sf.ehcache.pool.Size;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * Created by Nikolas on 2015/9/25.
 */
public class MySessionValidationScheduler implements SessionValidationScheduler, Runnable {

    private JdbcTemplate jdbcTemplate= JdbcTemplateUtils.getInstance();

    private static final Logger log= LoggerFactory.getLogger(MySessionValidationScheduler.class);

    ValidatingSessionManager sessionManager;

    private ScheduledExecutorService service;
    private long interval= DefaultSessionManager.DEFAULT_SESSION_VALIDATION_INTERVAL;
    private boolean enabled=true;

    @Override
    public void run() {
        if(log.isDebugEnabled()){
            log.debug("Executing session validation...");
        }
        long startTime=System.currentTimeMillis();
        String sql = "select session from sessions limit ? , ?";
        int start=0;
        int size=20;
        List<String> sessionList = jdbcTemplate.queryForList(sql, String.class, start, size);
        while(sessionList.size()>0){
            for(String sessionStr:sessionList){
                Session session= SerializableUtils.deserialize(sessionStr);
                Method validateMethod = ReflectionUtils.findMethod(AbstractValidatingSessionManager.class, "validate", Session.class, SessionKey.class);
                ReflectionUtils.invokeMethod(validateMethod, sessionManager, session, new DefaultSessionKey(session.getId()));
                start+=size;
                sessionList=jdbcTemplate.queryForList(sql, String.class, start,size);
            }
        }
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public ValidatingSessionManager getSessionManager() {
        return sessionManager;
    }

    public void setSessionManager(ValidatingSessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    public long getInterval() {
        return interval;
    }

    public void setInterval(long interval) {
        this.interval = interval;
    }

    @Override
    public void enableSessionValidation() {
        if(interval>1){
            service= Executors.newSingleThreadScheduledExecutor(new ThreadFactory() {
                @Override
                public Thread newThread(Runnable r) {
                    Thread thread=new Thread(r);
                    thread.setDaemon(true);
                    return thread;
                }
            });
            service.scheduleAtFixedRate(this, interval,interval, TimeUnit.MILLISECONDS);
            this.enabled=true;
        }
    }

    @Override
    public void disableSessionValidation() {
        this.service.shutdown();
        this.enabled=false;
    }
}
