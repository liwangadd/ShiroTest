package com.shiro.test;

import com.sun.org.apache.xpath.internal.SourceTree;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.codec.CodecSupport;
import org.apache.shiro.codec.Hex;
import org.apache.shiro.crypto.AesCipherService;
import org.apache.shiro.crypto.BlowfishCipherService;
import org.apache.shiro.crypto.DefaultBlockCipherService;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.*;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.SimpleByteSource;
import org.junit.Assert;
import org.junit.Test;
import java.security.Key;

/**
 * Created by Nikolas on 2015/9/23.
 */
public class CodecAndCryptoTest {

    @Test
    public void testBase64() {
        String str = "hello";
        String base64Encodeed = Base64.encodeToString(str.getBytes());
        String str2 = Base64.decodeToString(base64Encodeed);
        Assert.assertEquals(str, str2);
    }

    @Test
    public void testHex() {
        String str = "hello";
        String base64Encoded = Hex.encodeToString(str.getBytes());
        String str2 = new String(Hex.decode(base64Encoded));
        Assert.assertEquals(str, str2);
    }

    @Test
    public void testCodecSupport() {
        String str = "hello";
        byte[] bytes = CodecSupport.toBytes(str, "utf-8");
        String str2 = CodecSupport.toString(bytes);
        Assert.assertEquals(str, str2);
    }

    @Test
    public void testRandom() {
        SecureRandomNumberGenerator generator = new SecureRandomNumberGenerator();
        generator.setSeed("123".getBytes());
        System.out.println(generator.nextBytes().toHex());
    }

    @Test
    public void testMd5() {
        String str = "hello";
        String salt = "123";
        String md5 = new Md5Hash(str, salt).toString();
        System.out.println(md5);
    }

    @Test
    public void testSha() {
        String str = "hello";
        String salt = "123";
        String sha1 = new Sha1Hash(str, salt).toString();
        String sha256 = new Sha256Hash(str, salt).toString();
        String sha384 = new Sha384Hash(str, salt).toString();
        String sha512 = new Sha512Hash(str, salt).toBase64();
        System.out.println(sha1);
        System.out.println(sha256);
        System.out.println(sha384);
        System.out.println(sha512);
    }

    @Test
    public void testSimpleHash() {
        String str = "hello";
        String salt = "123";
        String simpleHash = new SimpleHash("sha-512", str, salt).toBase64();
        System.out.println(simpleHash);
    }

    @Test
    public void testHashService() {
        DefaultHashService hashService = new DefaultHashService(); //默认算法SHA-512
        hashService.setHashAlgorithmName("SHA-512");
        hashService.setPrivateSalt(new SimpleByteSource("123")); //私盐，默认无
        hashService.setGeneratePublicSalt(true);//是否生成公盐，默认false
        hashService.setRandomNumberGenerator(new SecureRandomNumberGenerator());//用于生成公盐。默认就这个
        hashService.setHashIterations(1);

        HashRequest request = new HashRequest.Builder().setAlgorithmName("md5").setSource(ByteSource.Util.bytes("hello"))
                .setSalt(ByteSource.Util.bytes("123")).setIterations(2).build();
        String hex = hashService.computeHash(request).toHex();
        System.out.println(hex);
    }

    @Test
    public void testAesCipherService() {
        AesCipherService cipherService = new AesCipherService();
        cipherService.setKeySize(128);

        Key key = cipherService.generateNewKey();
        String text = "hello";

        String encryptTest = cipherService.encrypt(text.getBytes(), key.getEncoded()).toHex();
        String text2 = new String(cipherService.decrypt(Hex.decode(encryptTest), key.getEncoded()).getBytes());
        Assert.assertEquals(text, text2);
    }

    @Test
    public void testBlowfishCipherService() {
        BlowfishCipherService cipherService = new BlowfishCipherService();
        cipherService.setKeySize(128);

        Key key = cipherService.generateNewKey();
        String text = "hello";

        String encryptText = cipherService.encrypt(text.getBytes(), key.getEncoded()).toHex();
        String text2 = new String(cipherService.decrypt(Hex.decode(encryptText.getBytes()), key.getEncoded()).getBytes());
        Assert.assertEquals(text, text2);
    }

    @Test
    public void testDefaultBlockCipherService() {
//        对称加密，使用Java的JCA（javax.crypto.Cipher）加密API，常见的如 'AES', 'Blowfish'
        DefaultBlockCipherService cipherService = new DefaultBlockCipherService("aes");
        cipherService.setKeySize(128);
        Key key = cipherService.generateNewKey();
        String str = "hello";
        String encryptText = cipherService.encrypt(str.getBytes(), key.getEncoded()).toHex();
        String text2 = new String(cipherService.decrypt(Hex.decode(encryptText.getBytes()), key.getEncoded()).getBytes());
        Assert.assertEquals(str, text2);
    }
}