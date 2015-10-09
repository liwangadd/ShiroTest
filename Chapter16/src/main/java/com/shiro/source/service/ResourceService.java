package com.shiro.source.service;

import com.shiro.source.dao.ResourceDao;
import com.shiro.source.entity.Resource;
import org.apache.shiro.authz.permission.WildcardPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by liwang on 15-10-9.
 */
@Service
public class ResourceService {

    @Autowired
    private ResourceDao resourceDao;

    public Resource createResource(Resource resource) {
        return resourceDao.createResource(resource);
    }

    public Resource updateResource(Resource resource) {
        return resourceDao.updateResource(resource);
    }

    public void deleteResource(long resourceId) {
        resourceDao.deleteResource(resourceId);
    }

    public Resource findOne(long resourceId) {
        return resourceDao.findOne(resourceId);
    }

    public List<Resource> findAll() {
        return resourceDao.findAll();
    }

    public Set<String> findPermission(Set<Long> resourceIds) {
        Set<String> permissions = new HashSet<>();
        for (long resourceId : resourceIds) {
            Resource resource = resourceDao.findOne(resourceId);
            if (resource != null && !StringUtils.isEmpty(resource.getPermission()))
                permissions.add(resource.getPermission());
        }
        return permissions;
    }

    public List<Resource> findMenus(Set<String> permissions) {
        List<Resource> allResources = findAll();
        List<Resource> menus = new ArrayList<>();
        for (Resource resource : allResources) {
            if (resource.isRootNode()) {
                continue;
            }
            if (resource.getType() != Resource.ResourceType.menu) {
                continue;
            }
            if (!hasPermission(permissions, resource)) {
                continue;
            }
            menus.add(resource);
        }
        return menus;
    }

    public boolean hasPermission(Set<String> permissions, Resource resource) {
        if (StringUtils.isEmpty(resource.getPermission())) {
            return true;
        }
        for (String permission : permissions) {
            WildcardPermission p1 = new WildcardPermission(permission);
            WildcardPermission p2 = new WildcardPermission(resource.getPermission());
            if (p1.implies(p2) || p2.implies(p1))
                return true;
        }
        return false;
    }

}
