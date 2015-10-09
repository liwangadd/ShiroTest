package com.shiro.source.service;

import com.shiro.source.dao.OrganizationDao;
import com.shiro.source.entity.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by liwang on 15-10-9.
 */
@Service
public class OrganizationService {

    @Autowired
    private OrganizationDao organizationDao;

    public Organization createOrganization(Organization organization) {
        return organizationDao.createOrganization(organization);
    }

    public Organization updateOrganization(Organization organization) {
        return organizationDao.updateOrganization(organization);
    }

    public void deleteOrganization(long organizationId) {
        organizationDao.deleteOrganization(organizationId);
    }

    public Organization findOne(long organizationId) {
        return organizationDao.findOne(organizationId);
    }

    public List<Organization> findAll() {
        return organizationDao.findAll();
    }

    public List<Organization> findAllWithExclude(Organization excludeOrganization) {
        return organizationDao.findAllWithExclude(excludeOrganization);
    }

    public void move(Organization source, Organization target) {
        organizationDao.move(source, target);
    }

}
