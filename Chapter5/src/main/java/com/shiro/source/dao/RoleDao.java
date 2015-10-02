package com.shiro.source.dao;

import com.shiro.source.entity.Role;

/**
 * Created by Nikolas on 2015/9/24.
 */
public interface RoleDao {
    public Role createRole(Role role);
    public void deleteRole(Long roleId);

    public void correlationPermissions(long roleId, Long... permissionId);
    public void uncorrelationPermissions(Long roleId, Long... permissionId);
}
