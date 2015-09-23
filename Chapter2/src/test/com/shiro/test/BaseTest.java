package com.shiro.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.*;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.apache.shiro.util.ThreadContext;
import org.junit.After;

/**
 * Created by Nikolas on 2015/9/22.
 */
public class BaseTest {

    @After
    public void tearDown(){
        ThreadContext.unbindSubject();
    }

    public void login(String iniPath,String username,String password){
        Factory<org.apache.shiro.mgt.SecurityManager> factory=new IniSecurityManagerFactory(iniPath);
        SecurityManager manager = factory.getInstance();
        SecurityUtils.setSecurityManager(manager);
        Subject subject = SecurityUtils.getSubject();
        AuthenticationToken token=new UsernamePasswordToken(username, password);
        subject.login(token);
    }

    public Subject subject(){
        return SecurityUtils.getSubject();
    }
}
