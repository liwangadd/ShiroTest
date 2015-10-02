package com.shiro.source.service;

import com.shiro.source.entity.Role;

/**
 * Created by Nikolas on 2015/9/24.
 */
public interface RoleService {

    public Role createRole(Role role);

    public void deleteRole(Long roleId);

    public void correlationPermissions(Long roleId, Long... permissionIds);

    public void uncorrelationPermissions(Long roleId, Long... permissionIds);
}
