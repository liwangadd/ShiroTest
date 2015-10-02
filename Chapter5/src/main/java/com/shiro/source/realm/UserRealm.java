package com.shiro.source.realm;

import com.shiro.source.entity.User;
import com.shiro.source.service.UserService;
import com.shiro.source.service.UserServiceImpl;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

/**
 * Created by Nikolas on 2015/9/24.
 */
public class UserRealm extends AuthorizingRealm {

    private UserService userService = new UserServiceImpl();

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String username = principalCollection.getPrimaryPrincipal().toString();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addRoles(userService.findRoles(username));
        info.setStringPermissions(userService.findPermissions(username));
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String username = authenticationToken.getPrincipal().toString();
        User user = userService.findByUsername(username);
        if (user == null) {
            throw new UnknownAccountException();//√ª’“µΩ’ ∫≈
        }

        if (Boolean.TRUE.equals(user.getLocked())) {
            throw new LockedAccountException(); //’ ∫≈À¯∂®
        }
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user.getUsername(), user.getPassword(),
                ByteSource.Util.bytes(user.getCredentialsSalt()), getName());
        return info;
    }
}
