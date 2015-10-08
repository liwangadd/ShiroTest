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
 * Created by liwang on 15-10-6.
 */
public class UserRealm extends AuthorizingRealm {

    private UserService userService=new UserServiceImpl();

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String username= (String) principalCollection.getPrimaryPrincipal();
        SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
        info.addRoles(userService.findRoles(username));
        info.addStringPermissions(userService.findPermissions(username));
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String username= (String) authenticationToken.getPrincipal();
        User user=userService.findByUsername(username);
        if(user==null){
            throw new UnknownAccountException();//没有找到账号
        }
        if(user.getLocked()){
            throw new LockedAccountException();//账号锁定
        }
        SimpleAuthenticationInfo info=new SimpleAuthenticationInfo(user.getUsername(), user.getPassword(),
                ByteSource.Util.bytes(user.getCredentialsSalt()), getName());
        return info;
    }

    public void clearAllCachedAuthorizationInfo() {
        getAuthorizationCache().clear();
    }

    public void clearAllCachedAuthenticationInfo() {
        getAuthenticationCache().clear();
    }

    public void clearAllCache() {
        clearAllCachedAuthenticationInfo();
        clearAllCachedAuthorizationInfo();
    }

}
