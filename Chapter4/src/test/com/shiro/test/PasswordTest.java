package com.shiro.test;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.converters.AbstractConverter;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.junit.Test;

/**
 * Created by Nikolas on 2015/9/23.
 */
public class PasswordTest extends BaseTest {

    @Test
    public void testPasswordServiceWithMyRealm() {
        login("classpath:shiro-passwordservice.ini", "wu", "123");
    }

    @Test
    public void testPasswordServiceWithJdbcRealm() {
        login("classpath:shiro-jdbc-passwordservice.ini", "wu", "123");
    }

    @Test
    public void testGeneratePassword() {
        String algorithmName = "md5";
        String username = "liu";
        String password = "123";
        String salt1 = username;
        String salt2 = new SecureRandomNumberGenerator().nextBytes().toHex();
        String encodePassword = new SimpleHash(algorithmName, password, salt1 + salt2, 2).toBase64();
        System.out.println(salt2);
        System.out.println(encodePassword);
    }

    @Test
    public void testHashedCredentialsMatcherWithMyRealm2() {
        //ʹ��testGeneratePassword���ɵ�ɢ������
        login("classpath:shiro-hashedCredentialsMatcher.ini", "liu", "123");
    }

    @Test
    public void testHashedCredentialsMatcherWithJdbcRealm() {
        BeanUtilsBean.getInstance().getConvertUtils().register(new EnumConverter(), JdbcRealm.SaltStyle.class);

        //ʹ��testGeneratePassword���ɵ�ɢ������
        login("classpath:shiro-jdbc-hashedCredentialsMatcher.ini", "liu", "123");
    }

    private class EnumConverter extends AbstractConverter {

        @Override
        protected String convertToString(Object value) throws Throwable {
            return ((Enum) value).name();
        }

        @Override
        protected Object convertToType(Class aClass, Object o) throws Throwable {
            return Enum.valueOf(aClass, o.toString());
        }

        @Override
        protected Class getDefaultType() {
            return null;
        }
    }

    @Test(expected = ExcessiveAttemptsException.class)
    public void testRetryLimitHashedCredentialsMatcherWithMyRealm() {
        for(int i = 1; i <= 5; i++) {
            try {
                login("classpath:shiro-retryLimitHashedCredentialsMatcher.ini", "liu", "234");
            } catch (Exception e) {/*ignore*/}
        }
        login("classpath:shiro-retryLimitHashedCredentialsMatcher.ini", "liu", "234");
    }
}
