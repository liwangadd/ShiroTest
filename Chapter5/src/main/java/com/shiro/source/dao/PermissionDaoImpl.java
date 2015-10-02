package com.shiro.source.dao;

import com.shiro.source.JdbcTemplateUtils;
import com.shiro.source.entity.Permission;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by Nikolas on 2015/9/24.
 */
public class PermissionDaoImpl implements PermissionDao {

    private JdbcTemplate jdbcTemplate= JdbcTemplateUtils.getInstance();

    @Override
    public Permission createPermission(Permission permission) {
        final String sql = "insert into sys_permissions (permission, description, available) values (?, ?, ?)";
        GeneratedKeyHolder holder=new GeneratedKeyHolder();

        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement statement=connection.prepareStatement(sql, new String[]{"id"});
                statement.setString(1, permission.getPermission());
                statement.setString(2, permission.getDescription());
                statement.setBoolean(3, permission.getAvailable());
                return statement;
            }
        },holder);
        permission.setId(holder.getKey().longValue());
        return permission;
    }

    @Override
    public void deletePermission(Long permissionId) {
        String sql = "delete from sys_roles_permissions where permission_id = ?";
        jdbcTemplate.update(sql, permissionId);
        sql = "delete from sys_permissions where id = ?";
        jdbcTemplate.update(sql, permissionId);
    }
}
