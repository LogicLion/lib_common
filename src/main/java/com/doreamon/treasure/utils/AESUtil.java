package com.doreamon.treasure.utils;

import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * aes加密、解密(注：这里的Base64.encodeToString和Base64.decode是安卓的Api,只能在安卓环境调用)
 */
public class AESUtil {

    // 字符串编码
    private static final String KEY_CHARSET = "UTF-8";

    // 算法方式
    private static final String KEY_ALGORITHM = "AES";

    // 算法/模式/填充
    private static final String CIPHER_ALGORITHM_CBC = "AES/CBC/PKCS5Padding";

    // 私钥大小128/192/256(bits)位 即：16/24/32bytes，暂时使用256，如果扩大需要更换java/jre里面的jar包
    private static final Integer PRIVATE_KEY_SIZE_BIT = 256;

    private static final Integer PRIVATE_KEY_SIZE_BYTE = 16;

    // 偏移量,可自行修改
//    private static final String VI = "a47d0242ac13016f";


    /**
     * 加密
     * @param secretKey 密钥：加密的规则 16位
     * @param plainText 明文：要加密的内容
     * @param vi 偏移量
     * @return cipherText 密文：加密后的内容，如有异常返回空串：""
     */
    public static String encrypt(String secretKey, String vi,String plainText) {
        if (secretKey.length() != PRIVATE_KEY_SIZE_BYTE) {
            throw new RuntimeException("Invalid AES secretKey length (must be 16 bytes)");
        }

        // 密文字符串
        String cipherText = "";
        try {
            // 加密模式初始化参数
            Cipher cipher = initParam(secretKey, vi,Cipher.ENCRYPT_MODE);
            // 获取加密内容的字节数组
            byte[] bytePlainText = plainText.getBytes(KEY_CHARSET);
            // 执行加密
            byte[] byteCipherText = cipher.doFinal(bytePlainText);
            cipherText = Base64.encodeToString(byteCipherText, Base64.NO_WRAP);

        } catch (Exception e) {
            throw new RuntimeException("encrypt fail!", e);
        }
        return cipherText;
    }



    /**
     * 解密
     * @param secretKey 密钥：加密的规则 16位
     * @param vi 偏移量
     * @param cipherText 密文：加密后的内容，即需要解密的内容
     * @return plainText  明文：解密后的内容即加密前的内容，如有异常返回空串：""
     */
    public static String decrypt(String secretKey, String vi ,String cipherText) {

//        if (secretKey.length() != PRIVATE_KEY_SIZE_BYTE) {
//            throw new RuntimeException("Invalid AES secretKey length (must be 16 bytes)");
//        }

        // 明文字符串
        String plainText = "";
        try {
            Cipher cipher = initParam(secretKey, vi, Cipher.DECRYPT_MODE);
            // 将加密并编码后的内容解码成字节数组
            byte[] byteCipherText = Base64.decode(cipherText, Base64.NO_WRAP);
            // 解密
            byte[] bytePlainText = cipher.doFinal(byteCipherText);
            plainText = new String(bytePlainText, KEY_CHARSET);
        } catch (Exception e) {
            throw new RuntimeException("decrypt fail!", e);
        }
        return plainText;
    }

    /**
     * 初始化参数
     * @param secretKey 密钥：加密的规则 16位
     * @param vi
     * @param mode
     */
    private static Cipher initParam(String secretKey, String vi, int mode) {
        try {
            // 防止Linux下生成随机key
//            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
//            secureRandom.setSeed(secretKey.getBytes());
//            // 获取key生成器
//            KeyGenerator keygen = KeyGenerator.getInstance(KEY_ALGORITHM);
//            keygen.init(PRIVATE_KEY_SIZE_BIT, secureRandom);

            // 获得原始对称密钥的字节数组
            byte[] raw = secretKey.getBytes();

            // 根据字节数组生成AES内部密钥
            SecretKeySpec key = new SecretKeySpec(raw, KEY_ALGORITHM);
            // 根据指定算法"AES/CBC/PKCS5Padding"实例化密码器
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_CBC);
            IvParameterSpec iv = new IvParameterSpec(vi.getBytes());

            // 初始化AES密码器
            cipher.init(mode, key, iv);

            return cipher;
        } catch (Exception e) {
            throw new RuntimeException("initParam fail!", e);
        }
    }
}
