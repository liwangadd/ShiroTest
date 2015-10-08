package com.shiro.source.dao;

import com.shiro.source.entity.Resource;

import java.util.List;

/**
 * Created by liwang on 15-10-7.
 */
public interface ResourceDao {
    public Resource createResource(Resource resource);
    public Resource updateResource(Resource resource);
    public void deleteResource(Long resourceId);

    Resource findOne(Long resourceId);
    List<Resource> findAll();
}
