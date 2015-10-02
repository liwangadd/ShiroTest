package com.shiro.source.dao;

import com.shiro.source.entity.Permission;

/**
 * Created by Nikolas on 2015/9/24.
 */
public interface PermissionDao {
    public Permission createPermission(Permission permission);

    public void deletePermission(Long permissionId);
}
