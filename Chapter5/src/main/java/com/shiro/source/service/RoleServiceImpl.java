package com.shiro.source.service;

import com.shiro.source.dao.RoleDao;
import com.shiro.source.dao.RoleDaoImpl;
import com.shiro.source.entity.Role;

/**
 * Created by Nikolas on 2015/9/24.
 */
public class RoleServiceImpl implements RoleService {

    private RoleDao roleService = new RoleDaoImpl();

    @Override
    public Role createRole(Role role) {
        return roleService.createRole(role);
    }

    @Override
    public void deleteRole(Long roleId) {
        roleService.deleteRole(roleId);
    }

    @Override
    public void correlationPermissions(Long roleId, Long... permissionIds) {
        roleService.correlationPermissions(roleId, permissionIds);
    }

    @Override
    public void uncorrelationPermissions(Long roleId, Long... permissionIds) {
        roleService.uncorrelationPermissions(roleId, permissionIds);
    }
}
