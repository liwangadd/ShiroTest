package com.shiro.test;

import org.apache.shiro.authz.UnauthorizedException;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 * Created by Nikolas on 2015/9/22.
 */
public class RoleTest extends BaseTest{

    @Test
    public void testHasRole(){
        login("classpath:shiro-role.ini", "zhang", "123");
        Assert.assertTrue(subject().hasRole("role1"));
        Assert.assertTrue(subject().hasAllRoles(Arrays.asList("role1", "role2")));
        boolean[] results = subject().hasRoles(Arrays.asList("role1", "role2", "role3"));
        Assert.assertEquals(true,results[0]);
        Assert.assertEquals(true,results[1]);
        Assert.assertEquals(false,results[2]);
    }

    @Test(expected = UnauthorizedException.class)
    public void testCheckRole(){
        login("classpath:shiro-role.ini","zhang","123");
        subject().checkRole("role1");
        subject().checkRoles("role1","role3");
    }
}
