package com.shiro.source.dao;

import com.shiro.source.entity.Role;

import java.util.List;

/**
 * Created by liwang on 15-10-7.
 */
public interface RoleDao {

    public Role createRole(Role role);
    public Role updateRole(Role role);
    public void deleteRole(Long roleId);

    public Role findOne(Long roleId);
    public List<Role> findAll();
}
