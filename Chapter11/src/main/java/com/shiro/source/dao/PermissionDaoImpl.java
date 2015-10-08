package com.shiro.source.dao;

import com.shiro.source.entity.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;

/**
 * Created by liwang on 15-10-6.
 */
@Repository
public class PermissionDaoImpl implements PermissionDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Permission createPermission(Permission permission) {
        final String sql = "INSERT INTO sys_permissions(permission, description, available) VALUES (?, ?, ?)";
        GeneratedKeyHolder holder=new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement=connection.prepareStatement(sql);
            statement.setString(1, permission.getPermission());
            statement.setString(2,permission.getDescription());
            statement.setBoolean(3, permission.getAvailable());
            return statement;
        }, holder);
        permission.setId(holder.getKey().longValue());
        return permission;
    }

    @Override
    public void deletePermission(long permissionId) {
        String sql = "DELETE FROM sys_roles_permissions WHERE permission_id = ?";
        jdbcTemplate.update(sql, permissionId);

        sql = "DELETE FROM sys_permissions WHERE permission_id = ?";
        jdbcTemplate.update(sql, permissionId);
    }
}
