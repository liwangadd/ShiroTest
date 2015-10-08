package com.shiro.source.dao;

import com.shiro.source.entity.User;

import java.util.List;

/**
 * Created by liwang on 15-10-7.
 */
public interface UserDao {

    public User createUser(User user);
    public User updateUser(User user);
    public void deleteUser(Long userId);

    User findOne(Long userId);

    List<User> findAll();

    User findByUsername(String username);
}
