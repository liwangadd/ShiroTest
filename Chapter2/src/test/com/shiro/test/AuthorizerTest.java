package com.shiro.test;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Nikolas on 2015/9/22.
 */
public class AuthorizerTest extends BaseTest {

    @Test
    public void testIsPermission(){
        login("classpath:shiro-authorizer.ini","zhang","123");
        Assert.assertTrue(subject().isPermitted("user1:update"));
        Assert.assertTrue(subject().isPermitted("user2:update"));
        //ͨ��������λ�ķ�ʽ��ʾȨ��
        Assert.assertTrue(subject().isPermitted("+user1+2"));//����Ȩ��
        Assert.assertTrue(subject().isPermitted("+user1+8"));//�鿴Ȩ��
        Assert.assertTrue(subject().isPermitted("+user2+10"));//�������鿴

        Assert.assertFalse(subject().isPermitted("+user1+4"));//û��ɾ��Ȩ��

        Assert.assertTrue(subject().isPermitted("menu:view"));//ͨ��MyRolePermissionResolver�����õ���Ȩ��
    }

    @Test
    public void testIsPermitted2(){
        login("classpath:shiro-jdbc-authorizer.ini","zhang","123");
        Assert.assertTrue(subject().isPermitted("user1:update"));
        Assert.assertTrue(subject().isPermitted("user2:update"));
        Assert.assertTrue(subject().isPermitted("+user1+2"));//����Ȩ��
        Assert.assertTrue(subject().isPermitted("+user1+8"));//�鿴Ȩ��
        Assert.assertTrue(subject().isPermitted("+user2+10"));//�������鿴

        Assert.assertFalse(subject().isPermitted("+user1+4"));//û��ɾ��Ȩ��

        Assert.assertTrue(subject().isPermitted("menu:view"));//ͨ��MyRolePermissionResolver�����õ���Ȩ��
    }
}
