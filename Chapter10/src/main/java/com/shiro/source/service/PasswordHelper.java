package com.shiro.source.service;

import com.shiro.source.entity.User;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * Created by Nikolas on 2015/9/24.
 */
public class PasswordHelper {

    private RandomNumberGenerator randomNumberGenerator=new SecureRandomNumberGenerator();

    private String algorithName="md5";
    private final int hashIterations=2;

    public void encryptPassword(User user){
        user.setSalt(randomNumberGenerator.nextBytes().toHex());
        String newPassword=new SimpleHash(algorithName, user.getPassword(), ByteSource.Util.bytes(user.getCredentialsSalt()), hashIterations).toHex();
        user.setPassword(newPassword);
    }
}
