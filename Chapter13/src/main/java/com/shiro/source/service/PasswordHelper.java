package com.shiro.source.service;

import com.shiro.source.entity.User;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * Created by Nikolas on 2015/9/24.
 */
public class PasswordHelper {

    private RandomNumberGenerator randomNumberGenerator;

    private String algorithName="md5";
    private int hashIterations=2;

    public void encryptPassword(User user){
        user.setSalt(randomNumberGenerator.nextBytes().toHex());
        String newPassword=new SimpleHash(algorithName, user.getPassword(), ByteSource.Util.bytes(user.getCredentialsSalt()), hashIterations).toHex();
        user.setPassword(newPassword);
    }

    public String getAlgorithName() {
        return algorithName;
    }

    public void setAlgorithName(String algorithName) {
        this.algorithName = algorithName;
    }

    public int getHashIterations() {
        return hashIterations;
    }

    public void setHashIterations(int hashIterations){
        this.hashIterations=hashIterations;
    }

    public RandomNumberGenerator getRandomNumberGenerator() {
        return randomNumberGenerator;
    }

    public void setRandomNumberGenerator(RandomNumberGenerator randomNumberGenerator) {
        this.randomNumberGenerator = randomNumberGenerator;
    }
}
