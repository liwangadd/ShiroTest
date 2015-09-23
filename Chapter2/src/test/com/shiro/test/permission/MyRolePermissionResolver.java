package com.shiro.test.permission;

import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.permission.RolePermissionResolver;
import org.apache.shiro.authz.permission.WildcardPermission;

import java.util.Arrays;
import java.util.Collection;

/**
 * Created by Nikolas on 2015/9/22.
 */
public class MyRolePermissionResolver implements RolePermissionResolver {

    @Override
    public Collection<Permission> resolvePermissionsInRole(String s) {
        if ("role1".equals(s)) {
            return Arrays.asList(new WildcardPermission("menu:*"));
        }
        return null;
    }
}
