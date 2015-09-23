package com.shiro.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.apache.shiro.util.ThreadContext;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Nikolas on 2015/9/21.
 */
public class AuthenticatorTest {

    @Test
    public void testAllSuccessfulStrategyWithSuccess(){
        login("classpath:shiro-authenticator-all-success.ini");
        Subject subject = SecurityUtils.getSubject();
        PrincipalCollection colle = subject.getPrincipals();
        Assert.assertEquals(2, colle.asList().size());
    }

    @Test(expected = UnknownAccountException.class)
    public void testAllSuccessfulStrategyWithFail(){
        login("classpath:shiro-authenticator-all-fail.ini");
    }

    @Test
    public void testAtLeastOneSuccessfulStrategyWithSuccess(){
        login("classpath:shiro-authenticator-atLeastOne-success.ini");
        Subject subject = SecurityUtils.getSubject();
        PrincipalCollection colle = subject.getPrincipals();
        Assert.assertEquals(2, colle.asList().size());
    }

    @Test
    public void testFirstOneSuccessfulStrategyWithSuccess() {
        login("classpath:shiro-authenticator-first-success.ini");
        Subject subject = SecurityUtils.getSubject();

        //�õ�һ����ݼ��ϣ�������˵�һ��Realm��֤�ɹ��������Ϣ
        PrincipalCollection principalCollection = subject.getPrincipals();
        Assert.assertEquals(1, principalCollection.asList().size());
    }

    @Test
    public void testAtLeastTwoStrategyWithSuccess() {
        login("classpath:shiro-authenticator-atLeastTwo-success.ini");
        Subject subject = SecurityUtils.getSubject();

        //�õ�һ����ݼ��ϣ���ΪmyRealm1��myRealm4���ص����һ���������ʱֻ����һ��
        PrincipalCollection principalCollection = subject.getPrincipals();
        Assert.assertEquals(1, principalCollection.asList().size());
    }

    @Test
    public void testOnlyOneStrategyWithSuccess() {
        login("classpath:shiro-authenticator-onlyone-success.ini");
        Subject subject = SecurityUtils.getSubject();

        //�õ�һ����ݼ��ϣ���ΪmyRealm1��myRealm4���ص����һ���������ʱֻ����һ��
        PrincipalCollection principalCollection = subject.getPrincipals();
        Assert.assertEquals(1, principalCollection.asList().size());
    }

    public void login(String configFile){
        Factory<org.apache.shiro.mgt.SecurityManager> factory=new IniSecurityManagerFactory(configFile);
        SecurityManager manager = factory.getInstance();
        SecurityUtils.setSecurityManager(manager);
        Subject subject = SecurityUtils.getSubject();
        AuthenticationToken token=new UsernamePasswordToken("zhang","123");
        subject.login(token);
    }

    public void tearDown(){
        ThreadContext.unbindSubject();
    }
}
