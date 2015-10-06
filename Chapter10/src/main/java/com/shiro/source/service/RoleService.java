package com.shiro.source.service;

import com.shiro.source.entity.Role;

/**
 * Created by Nikolas on 2015/9/24.
 */
public interface RoleService {

    public Role createRole(Role role);

    public void deleteRole(long roleId);

    public void correlationPermissions(long roleId, long... permissionIds);

    public void uncorrelationPermissions(long roleId, long... permissionIds);
}
