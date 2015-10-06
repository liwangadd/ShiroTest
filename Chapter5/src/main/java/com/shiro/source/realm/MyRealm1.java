package com.shiro.source.realm;

import org.apache.shiro.authc.*;
import org.apache.shiro.realm.Realm;

/**
 * Created by Nikolas on 2015/9/24.
 */
public class MyRealm1 implements Realm {

    @Override
    public String getName() {
        return "a"; //realm name Ϊ ��a��
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof UsernamePasswordToken;
    }

    @Override
    public AuthenticationInfo getAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        return new SimpleAuthenticationInfo(
                "zhang", //username
                "123",   //password
                getName() //Realm Name
        );
    }
}
