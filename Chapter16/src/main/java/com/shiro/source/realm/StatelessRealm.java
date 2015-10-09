package com.shiro.source.realm;

import com.shiro.source.codec.HmacSHA256Utils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * Created by Nikolas on 2015/10/8.
 */
public class StatelessRealm extends AuthorizingRealm {
    @Override
    public boolean supports(AuthenticationToken token) {
        //��֧��StatelessToken���͵�Token
        return token instanceof StatelessToken;
    }
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //�����û������ҽ�ɫ�����������ʵ��
        String username = (String) principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo =  new SimpleAuthorizationInfo();
        authorizationInfo.addRole("admin");
        return authorizationInfo;
    }
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        StatelessToken statelessToken = (StatelessToken) token;
        String username = statelessToken.getUsername();
        String key = getKey(username);//�����û�����ȡ��Կ���Ϳͻ��˵�һ����
        //�ڷ����������ɿͻ��˲�����ϢժҪ
        String serverDigest = HmacSHA256Utils.digest(key, statelessToken.getParams());
        System.out.println(statelessToken.getClientDigest());
        System.out.println(serverDigest);
        //Ȼ����пͻ�����ϢժҪ�ͷ���������ϢժҪ��ƥ��
        return new SimpleAuthenticationInfo(
                username,
                serverDigest,
                getName());
    }

    private String getKey(String username) {//�õ���Կ���˴�Ӳ����һ��
        if("admin".equals(username)) {
            return "dadadswdewq2ewdwqdwadsadasd";
        }
        return null;
    }
}
