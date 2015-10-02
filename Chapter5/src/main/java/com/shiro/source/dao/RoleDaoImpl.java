package com.shiro.source.dao;

import com.shiro.source.JdbcTemplateUtils;
import com.shiro.source.entity.Role;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by Nikolas on 2015/9/24.
 */
public class RoleDaoImpl implements RoleDao {
    private JdbcTemplate jdbcTemplate = JdbcTemplateUtils.getInstance();

    @Override
    public Role createRole(final Role Role) {
        final String sql = "insert into sys_roles(role, description, available) values(?,?,?)";

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement psst = connection.prepareStatement(sql, new String[] { "id" });
                psst.setString(1, Role.getRole());
                psst.setString(2, Role.getDescription());
                psst.setBoolean(3, Role.getAvailable());
                return psst;
            }
        }, keyHolder);
        Role.setId(keyHolder.getKey().longValue());

        return Role;
    }

    @Override
    public void deleteRole(Long roleId) {
        String sql = "delete from sys_users_roles where role_id = ?";
        jdbcTemplate.update(sql, roleId);
        sql = "delete from sys_roles where id = ?";
        jdbcTemplate.update(sql, roleId);
    }

    @Override
    public void correlationPermissions(long roleId, Long... permissionId) {
        if (permissionId == null || roleId == 0) {
            return;
        }
        String sql = "insert into sys_roles_permissions (role_id, permission_id) values (?,?)";
        for (Long permission : permissionId) {
            if (!exist(roleId, permission))
                jdbcTemplate.update(sql, roleId, permission);
        }
    }

    @Override
    public void uncorrelationPermissions(Long roleId, Long... permissionId) {
        if (permissionId == null || roleId == 0) {
            return;
        }
        String sql = "delete from sys_roles_permissions where role_id = ? and permission_id = ?";
        for (Long permission : permissionId) {
            jdbcTemplate.update(sql, roleId, permission);
        }
    }

    public boolean exist(Long roleId, Long permissionId) {
        String sql = "select count(1) from sys_roles_permissions where role_id = ? and permission_id = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, roleId, permissionId) != 0;
    }
}
