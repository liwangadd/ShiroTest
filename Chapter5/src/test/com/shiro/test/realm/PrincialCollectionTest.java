package com.shiro.test.realm;

import com.shiro.source.entity.User;
import com.shiro.test.BaseTest;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.junit.Assert;
import org.junit.Test;

import javax.security.auth.login.LoginContext;
import java.util.Collection;
import java.util.Set;

/**
 * Created by Nikolas on 2015/9/24.
 */
public class PrincialCollectionTest extends BaseTest{

    @Test
    public void test(){
        login("classpath:shiro-multi-realm.ini","zhang","123");
        Subject subject=subject();
        Object primaryPrincipall=subject.getPrincipal();
        PrincipalCollection principalCollection=subject.getPrincipals();
        Object primaryPrincipall2=principalCollection.getPrimaryPrincipal();
        Assert.assertEquals(primaryPrincipall, primaryPrincipall2);

        Set<String> realmNames=principalCollection.getRealmNames();
        System.out.println(realmNames);

        Set<Object> principals=principalCollection.asSet();
        System.out.println(principals);

        Collection<User> users=principalCollection.fromRealm("c");
        System.out.println(users);
    }
}
