package com.shiro.source.realm;

import com.shiro.source.entity.User;
import org.apache.shiro.authc.*;
import org.apache.shiro.realm.Realm;

/**
 * Created by Nikolas on 2015/9/24.
 */
public class MyRealm3 implements Realm {

    @Override
    public String getName() {
        return "c"; //realm name Ϊ ��c��
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof UsernamePasswordToken;
    }

    @Override
    public AuthenticationInfo getAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        User user = new User("zhang", "123");
        return new SimpleAuthenticationInfo(
                user, //��� User����
                "123",   //ƾ��
                getName() //Realm Name
        );
    }
}
