package com.shiro.source.dao;

import com.shiro.source.entity.User;

import java.util.Set;

/**
 * Created by Nikolas on 2015/9/24.
 */
public interface UserDao {
    public User createUser(User user);

    public void updateUser(User user);

    public void deleteUser(Long userId);

    public void correlationRoles(Long userId, Long... roleIds);

    public void uncorrelationRoles(Long userId, Long... roleIds);

    User findOne(Long userId);

    User findByUsername(String username);

    Set<String> findRoles(String username);

    Set<String> findPermissions(String username);
}
