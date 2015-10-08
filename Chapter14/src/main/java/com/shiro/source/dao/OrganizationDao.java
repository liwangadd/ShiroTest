package com.shiro.source.dao;

import com.shiro.source.entity.Organization;

import java.util.List;

/**
 * Created by liwang on 15-10-7.
 */
public interface OrganizationDao {

    public Organization createOrganization(Organization organization);
    public Organization updateOrganization(Organization organization);
    public void deleteOrganization(long organizationId);

    Organization findOne(long organizationId);
    List<Organization> findAll();

    List<Organization> findAllWithExclude(Organization excludeOrganization);
    void move(Organization source, Organization target);
}
