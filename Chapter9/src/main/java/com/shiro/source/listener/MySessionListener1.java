package com.shiro.source.listener;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;

/**
 * Created by Nikolas on 2015/9/25.
 */
public class MySessionListener1 implements SessionListener {
    @Override
    public void onStart(Session session) {
        System.out.println("create session");
    }

    @Override
    public void onStop(Session session) {
        System.out.println("destroy session");
    }

    @Override
    public void onExpiration(Session session) {
        System.out.println("session is expirated");
    }
}
