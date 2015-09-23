package com.shiro.test.realm;

import com.shiro.test.permission.BitPermission;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.authz.permission.WildcardPermission;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * Created by Nikolas on 2015/9/22.
 */
public class MyRealm extends AuthorizingRealm {
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
        info.addRole("role1");
        info.addRole("role2");
        info.addObjectPermission(new BitPermission("+user1+10"));
        info.addObjectPermission(new WildcardPermission("user1:*"));
        info.addStringPermission("+user2+10");
        info.addStringPermission("user2:*");
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String)token.getPrincipal();  //�õ��û���
        String password = new String((char[])token.getCredentials()); //�õ�����
        if(!"zhang".equals(username)) {
            throw new UnknownAccountException(); //����û�������
        }
        if(!"123".equals(password)) {
            throw new IncorrectCredentialsException(); //����������
        }
        //��������֤��֤�ɹ�������һ��AuthenticationInfoʵ�֣�
        return new SimpleAuthenticationInfo(username, password, getName());
    }
}
