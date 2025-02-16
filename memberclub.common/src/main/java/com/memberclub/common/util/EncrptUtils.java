/**
 * @(#)EncrptUtils.java, 十二月 29, 2024.
 * <p>
 * Copyright 2024 memberclub.com. All rights reserved.
 * memberclub.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.common.util;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * author: 掘金五阳
 */
public class EncrptUtils {


    // AES密钥算法
    private static final String KEY_ALGORITHM = "AES";

    // 加密/解密算法/工作模式/填充方式
    private static final String CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";

    // 生成密钥
    public static String generateAESKey() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(KEY_ALGORITHM);
        keyGenerator.init(128, new SecureRandom()); // 192 and 256 bits may not be available
        SecretKey secretKey = keyGenerator.generateKey();
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }

    // AES加密
    public static String encryptAES(String data, String key) throws Exception {
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        SecretKeySpec secretKeySpec = new SecretKeySpec(Base64.getDecoder().decode(key), KEY_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        byte[] encrypted = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encrypted);
    }

    public static void main(String[] args) throws Exception {
        String key = generateAESKey();
        String originalText = "Hello World!";
        String encryptedText = encryptAES(originalText, key);
        System.out.println("Encrypted Text: " + encryptedText);
        // 解密操作
        String decryptedText = decrypt(encryptedText, key);
        System.out.println("Decrypted Text: " + decryptedText);
    }

    public static String decrypt(String encrypt, String key) throws Exception {
        //密钥base64解码
        byte[] decode = Base64.getDecoder().decode(key);
        SecretKeySpec secretKeySpec = new SecretKeySpec(decode, KEY_ALGORITHM);
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        //初始化对象为解密模式
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        byte[] dateBytes = Base64.getDecoder().decode(encrypt);
        //解密并转换为字符串
        return new String(cipher.doFinal(dateBytes));
    }

    public static String generatePhoneMask(String phone) {
        return phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
    }
}