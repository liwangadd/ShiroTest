package com.shiro.source.dao;

import com.shiro.source.JdbcTemplateUtils;
import com.shiro.source.entity.Role;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import java.sql.PreparedStatement;

/**
 * Created by liwang on 15-10-6.
 */
public class RoleDaoImpl implements RoleDao {

    private JdbcTemplate jdbcTemplate = JdbcTemplateUtils.getInstance();

    @Override
    public Role createRole(Role role) {
        final String sql = "INSERT INTO sys_roles(role, description, available) values (?, ?, ?)";

        GeneratedKeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, role.getRole());
            statement.setString(2, role.getDescription());
            statement.setBoolean(3, role.getAvailable());
            return statement;
        }, holder);
        role.setId(holder.getKey().longValue());
        return role;
    }

    @Override
    public void deleteRole(long roleId) {
        String sql = "DELETE FROM sys_users_roles WHERE role_id = ?";
        jdbcTemplate.update(sql, roleId);

        sql = "DELETE FROM sys_roles WHERE id = ?";
        jdbcTemplate.update(sql, roleId);
    }

    @Override
    public void correlationPermissions(long roleId, long... permissionIds) {
        if (permissionIds == null || permissionIds.length == 0) {
            return;
        }
        final String sql = "INSERT INTO sys_roles_permissions(role_id, permission_id) VALUES (?, ?)";
        for (long permissionId : permissionIds) {
            if (!exists(roleId, permissionId))
                jdbcTemplate.update(sql, roleId, permissionId);
        }
    }

    @Override
    public void uncorrelationPermissions(long roleId, long... permissionIds) {
        if(permissionIds == null || permissionIds.length == 0) {
            return;
        }
        String sql = "DELETE FROM sys_roles_permissions WHERE role_id=? AND permission_id=?";
        for(Long permissionId : permissionIds) {
            if(exists(roleId, permissionId)) {
                jdbcTemplate.update(sql, roleId, permissionId);
            }
        }
    }

    private boolean exists(Long roleId, Long permissionId) {
        String sql = "select count(1) from sys_roles_permissions where role_id=? and permission_id=?";
        return jdbcTemplate.queryForObject(sql, Integer.class, roleId, permissionId) != 0;
    }
}
