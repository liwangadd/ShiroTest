package com.shiro.source.dao;

import com.shiro.source.entity.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by liwang on 15-10-7.
 */
@Repository
public class OrganizationDaoImpl implements OrganizationDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Organization createOrganization(Organization organization) {
        final String sql = "insert into sys_organization( name, parent_id, parent_ids, available) values(?,?,?,?)";
        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, organization.getName());
            statement.setLong(2, organization.getParentId());
            statement.setString(3, organization.getParentIds());
            statement.setBoolean(4, organization.getAvailable());
            return statement;
        }, holder);
        organization.setId(holder.getKey().longValue());
        return organization;
    }

    @Override
    public Organization updateOrganization(Organization organization) {
        final String sql = "update sys_organization set name=?, parent_id=?, parent_ids=?, available=? where id=?";
        jdbcTemplate.update(
                sql,
                organization.getName(), organization.getParentId(), organization.getParentIds(), organization.getAvailable(), organization.getId());
        return organization;
    }

    @Override
    public void deleteOrganization(long organizationId) {
        Organization organization = findOne(organizationId);
        final String deleteSelfSql = "delete from sys_organization where id=?";
        jdbcTemplate.update(deleteSelfSql, organizationId);
        final String deleteDescendantsSql = "delete from sys_organization where parent_ids like ?";
        jdbcTemplate.update(deleteDescendantsSql, organization.makeSelfAsParentIds() + "%");
    }

    @Override
    public Organization findOne(long organizationId) {
        final String sql = "select id, name, parent_id, parent_ids, available from sys_organization where id=?";
        List<Organization> organizationList = jdbcTemplate.query(sql, new BeanPropertyRowMapper(Organization.class), organizationId);
        if(organizationList.size() == 0) {
            return null;
        }
        return organizationList.get(0);
    }

    @Override
    public List<Organization> findAll() {
        final String sql = "SELECT id, name, parent_id, parent_ids, available from sys_organization";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Organization.class));
    }

    @Override
    public List<Organization> findAllWithExclude(Organization excludeOrganization) {
        final String sql = "SELECT id, name, parent_id, parent_ids, available from sys_organization where id != ? and parent_ids not like ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Organization.class), excludeOrganization.getId(), excludeOrganization.getParentIds() + "%");
    }

    @Override
    public void move(Organization source, Organization target) {
        String moveSourceSql = "update sys_organization set parent_id=?,parent_ids=? where id=?";
        jdbcTemplate.update(moveSourceSql, target.getId(), target.getParentIds(), source.getId());
        String moveSourceDescendantsSql = "update sys_organization set parent_ids=concat(?, substring(parent_ids, length(?))) where parent_ids like ?";
        jdbcTemplate.update(moveSourceDescendantsSql, target.makeSelfAsParentIds(), source.makeSelfAsParentIds(), source.makeSelfAsParentIds() + "%");
    }
}
