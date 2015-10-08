package com.shiro.source.dao;

import com.shiro.source.JdbcTemplateUtils;
import com.shiro.source.entity.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Nikolas on 2015/9/24.
 */
public class UserDaoImpl implements UserDao {

    private JdbcTemplate jdbcTemplate = JdbcTemplateUtils.getInstance();

    @Override
    public User createUser(User user) {
        final String sql = "insert into sys_users (username, password, salt, locked) values (?, ?, ?, ?)";

        GeneratedKeyHolder holder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(sql, new String[]{"id"});
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getSalt());
            statement.setBoolean(4, user.getLocked());
            return statement;
        }, holder);
        user.setId(holder.getKey().longValue());
        return user;
    }

    @Override
    public void updateUser(User user) {
        String sql = "update sys_users set username = ?, password = ?, salt = ?, locked = ? where id = ?";
        jdbcTemplate.update(sql, user.getUsername(), user.getPassword(), user.getSalt(), user.getSalt());
    }

    @Override
    public void deleteUser(Long userId) {
        String sql = "delete from sys_users where id = ?";
        jdbcTemplate.update(sql, userId);
    }

    @Override
    public void correlationRoles(Long userId, Long... roleIds) {
        if (roleIds == null || roleIds.length == 0) {
            return;
        }
        String sql = "insert into sys_users_roles(user_id, role_id) values (?, ?)";
        for (Long roleId : roleIds) {
            jdbcTemplate.update(sql, userId, roleId);
        }
    }

    @Override
    public void uncorrelationRoles(Long userId, Long... roleIds) {
        if (roleIds == null || roleIds.length == 0) {
            return;
        }
        String sql = "delete from sys_users_roles where user_id = ? and role_id = ?";
        for (Long roleId : roleIds) {
            jdbcTemplate.update(sql, userId, roleId);
        }
    }

    @Override
    public User findOne(Long userId) {
        String sql="select id, username, password, salt, locked from sys_users where id = ?";
        List<User> users = jdbcTemplate.query(sql, new BeanPropertyRowMapper(User.class), userId);
        if(users.size()==0){
            return null;
        }
        return users.get(0);
    }

    @Override
    public User findByUsername(String username) {
        String sql = "select id, username, password, salt, locked from sys_users where username = ?";
        List<User> users = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class), username);
        if(users.size()==0){
            return null;
        }
        return users.get(0);
    }

    @Override
    public Set<String> findRoles(String username) {
        String sql="select role from sys_users u, sys_roles r, sys_users_roles ur where u.username = ? and u.id = ur.user_id and r.id = ur.role_id";
        return new HashSet<>(jdbcTemplate.queryForList(sql, String.class, username));
    }

    @Override
    public Set<String> findPermissions(String username) {
        //TODO �˴������Ż��������ѯ��role��һ���ȡroleId��Ȼ��ֱ�Ӹ���roleId��ȡ����
        String sql = "select permission from sys_users u, sys_roles r, sys_permissions p, sys_users_roles ur, sys_roles_permissions rp where u.username=? and u.id=ur.user_id and r.id=ur.role_id and r.id=rp.role_id and p.id=rp.permission_id";
        return new HashSet<>(jdbcTemplate.queryForList(sql, String.class, username));
    }
}
