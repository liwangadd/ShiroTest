package com.shiro.source.dao;

import com.shiro.source.entity.Role;

/**
 * Created by liwang on 15-10-6.
 */
public interface RoleDao {

    public Role createRole(Role role);

    public void deleteRole(long roleId);

    public void correlationPermissions(long roleId, long... permissionIds);

    public void uncorrelationPermissions(long roleId, long... permissionIds);

}
