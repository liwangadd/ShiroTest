package com.shiro.source.dao;

import com.shiro.source.entity.User;

import java.util.Set;

/**
 * Created by liwang on 15-10-6.
 */
public interface UserDao {

    public User createUser(User user);

    public void deleteUser(long userId);

    public void updateUser(User user);

    public void correlationRoles(long userId, long... roleIds);

    public void uncorrelationRoles(long userId, long... roleIds);

    public User findOne(long userId);

    public User findByUsername(String username);

    Set<String> findRoles(String username);

    Set<String> findPermissions(String username);

}
