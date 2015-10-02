package com.shiro.source.service;

import com.shiro.source.dao.PermissionDao;
import com.shiro.source.dao.PermissionDaoImpl;
import com.shiro.source.entity.Permission;

/**
 * Created by Nikolas on 2015/9/24.
 */
public class PermissionServiceImpl implements PermissionService {

    private PermissionDao permissionDao=new PermissionDaoImpl();

    @Override
    public Permission createPermission(Permission permission) {
        return permissionDao.createPermission(permission);
    }

    @Override
    public void deletePermission(Long permissionId) {
        permissionDao.deletePermission(permissionId);
    }
}
