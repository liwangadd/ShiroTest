package com.shiro.source.dao;

import com.shiro.source.entity.Permission;

/**
 * Created by liwang on 15-10-6.
 */
public interface PermissionDao {

    public Permission createPermission(Permission permission);

    public void deletePermission(long permissionId);

}
