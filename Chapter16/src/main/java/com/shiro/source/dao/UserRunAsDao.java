package com.shiro.source.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by liwang on 15-10-9.
 */
@Repository
public class UserRunAsDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void grantRunAs(long fromUserId, long toUserId) {
        String sql = "insert into sys_user_runas (from_user_id, to_user_id) values (?, ?)";
        if (!exists(fromUserId, toUserId))
            jdbcTemplate.update(sql, fromUserId, toUserId);
    }

    public boolean exists(long fromUserId, long toUserId) {
        String sql = "select count(1) from sys_user_runas where from_user_id = ? and to_user_id = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, fromUserId, toUserId) != 0;
    }

    public void revokeRunAs(long fromUserId, long toUserId) {
        String sql = "delete from sys_user_runas where from_user_id = ? and to_user_id = ?";
        jdbcTemplate.update(sql, fromUserId, toUserId);
    }

    public List<Long> findFromUserIds(long toUserId) {
        String sql = "select from_user_id from sys_user_runas where to_user_id = ?";
        return jdbcTemplate.queryForList(sql, Long.class, toUserId);
    }

    public List<Long> findToUserIds(long fromUserId) {
        String sql = "select to_user_id from sys_user_runas where from_user_id = ?";
        return jdbcTemplate.queryForList(sql, Long.class, fromUserId);
    }

}
