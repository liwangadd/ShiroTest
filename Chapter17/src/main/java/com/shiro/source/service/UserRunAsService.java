package com.shiro.source.service;

import com.shiro.source.dao.UserRunAsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by liwang on 15-10-9.
 */
@Service
public class UserRunAsService {

    @Autowired
    private UserRunAsDao userRunAsDao;

    public void grantRunAs(long fromUserId, long toUserId) {
        userRunAsDao.grantRunAs(fromUserId, toUserId);
    }

    public void revokeRunAs(long fromUserId, long toUserId) {
        userRunAsDao.revokeRunAs(fromUserId, toUserId);
    }

    public boolean exists(long fromUserId, long toUserId) {
        return userRunAsDao.exists(fromUserId, toUserId);
    }

    public List<Long> findFromUserIds(long toUserId) {
        return userRunAsDao.findFromUserIds(toUserId);
    }

    public List<Long> findToUserIds(long fromUserId) {
        return userRunAsDao.findToUserIds(fromUserId);
    }

}
