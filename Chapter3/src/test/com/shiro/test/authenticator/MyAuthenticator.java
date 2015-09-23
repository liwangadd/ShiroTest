package com.shiro.test.authenticator;

import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.realm.Realm;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;

/**
 * Created by Nikolas on 2015/9/23.
 */
public class MyAuthenticator extends ModularRealmAuthenticator{

    public void setBytes(byte[] bytes){
        System.out.println(new String(bytes));
    }

    public void setArray(int[] ints){
        System.out.println(Arrays.toString(ints));
    }

    public void setSet(Set<Realm> realms){
        System.out.println(realms);
    }

    public void setMap(Map<Object,Object> maps){
        System.out.println(maps);
        System.out.println(maps.get("1"));
    }

}
