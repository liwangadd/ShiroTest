package com.shiro.source.service;

import com.shiro.source.dao.RoleDao;
import com.shiro.source.entity.Role;
import com.sun.org.apache.xerces.internal.impl.xpath.XPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by liwang on 15-10-9.
 */
@Service
public class RoleService {

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private ResourceService resourceService;

    public Role createRole(Role role) {
        return roleDao.createRole(role);
    }

    public Role updateRole(Role role) {
        return roleDao.updateRole(role);
    }

    public void deleteRole(long roleId) {
        roleDao.deleteRole(roleId);
    }

    public Role findOne(long roleId) {
        return roleDao.findOne(roleId);
    }

    public List<Role> findAll() {
        return roleDao.findAll();
    }

    public Set<String> findRoles(Long[] roleIds) {
        Set<String> roles = new HashSet<>();
        for (long roleId : roleIds) {
            Role role = findOne(roleId);
            if (role != null) {
                roles.add(role.getRole());
            }
        }
        return roles;
    }

    public Set<String> findPermissions(Long[] roleIds) {
        Set<Long> resourceIds = new HashSet<Long>();
        for (Long roleId : roleIds) {
            Role role = findOne(roleId);
            if (role != null) {
                resourceIds.addAll(role.getResourceIds());
            }
        }
        return resourceService.findPermission(resourceIds);
    }

}
