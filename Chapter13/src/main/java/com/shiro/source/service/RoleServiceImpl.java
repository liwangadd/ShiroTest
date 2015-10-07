package com.shiro.source.service;

import com.shiro.source.dao.RoleDao;
import com.shiro.source.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Nikolas on 2015/9/24.
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

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
