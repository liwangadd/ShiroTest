package com.shiro.source.service;

import com.shiro.source.dao.PermissionDao;
import com.shiro.source.entity.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Nikolas on 2015/9/24.
 */
@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionDao permissionDao;

    @Override
    public Permission createPermission(Permission permission) {
        return permissionDao.createPermission(permission);
    }

    @Override
    public void deletePermission(long permissionId) {
        permissionDao.deletePermission(permissionId);
    }
}
