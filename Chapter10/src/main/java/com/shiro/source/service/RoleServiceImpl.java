package com.shiro.source.service;

import com.shiro.source.dao.RoleDao;
import com.shiro.source.dao.RoleDaoImpl;
import com.shiro.source.entity.Role;

/**
 * Created by Nikolas on 2015/9/24.
 */
public class RoleServiceImpl implements RoleService {

    private RoleDao roleDao = new RoleDaoImpl();

    @Override
    public Role createRole(Role role) {
        return roleDao.createRole(role);
    }

    @Override
    public void deleteRole(long roleId) {
        roleDao.deleteRole(roleId);
    }

    @Override
    public void correlationPermissions(long roleId, long... permissionIds) {
        roleDao.correlationPermissions(roleId, permissionIds);
    }

    @Override
    public void uncorrelationPermissions(long roleId, long... permissionIds) {
        roleDao.uncorrelationPermissions(roleId, permissionIds);
    }
}
