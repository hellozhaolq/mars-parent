package com.zhaolq.core.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

/**
 * 哈希工具类
 *
 * @author zhaolq
 * @date 2020/11/12 16:16
 */
public class HashUtils {

    private final static String[] hexDigits = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    private final static String MD5 = "MD5";
    private final static String SHA_1 = "SHA-1";

    /* SHA-2 */

    private final static String SHA_224 = "SHA-224";
    private final static String SHA_256 = "SHA-256";
    private final static String SHA_384 = "SHA-384";
    private final static String SHA_512 = "SHA-512";
    private final static String SHA_512_224 = "SHA-512/224";
    private final static String SHA_512_256 = "SHA_512/256";

    /* SHA-3 */


    /**
     * 哈希计算
     *
     * @param content 内容
     * @param algorithm 算法
     * @return java.lang.String
     */
    public static String compute(String content, String algorithm) {
        return HashUtils.compute(content, null, null, algorithm);
    }

    /**
     * 哈希计算
     *
     * @param content 内容
     * @param salt 盐
     * @param algorithm 算法
     * @return java.lang.String
     */
    public static String compute(String content, String salt, String algorithm) {
        return HashUtils.compute(content, null, salt, algorithm);
    }

    /**
     * 哈希计算
     *
     * @param content 内容
     * @param contentCharset 内容字符集
     * @param salt 盐
     * @param algorithm 算法
     * @return java.lang.String
     */
    public static String compute(String content, String contentCharset, String salt, String algorithm) {
        String result = null;
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            result = byteArrayToHex(md.digest(mergeContentAndSalt(content, salt).getBytes(contentCharset))).toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 字节数组转换为16进制字符串
     *
     * @param byArr
     * @return java.lang.String
     */
    private static String byteArrayToHex(byte[] byArr) {
        StringBuffer sb = new StringBuffer(byArr.length * 2);
        for (int i = 0; i < byArr.length; i++) {
            sb.append(Character.forDigit((byArr[i] & 0xf0) >> 4, 16));
            sb.append(Character.forDigit(byArr[i] & 0x0f, 16));
        }
        return sb.toString();
    }

    /**
     * 字节数组转16进制字符串
     *
     * @param byArr 字节数组
     * @return java.lang.String 16进制字串
     */
    private String byteArrayToHex2(byte[] byArr) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < byArr.length; i++) {
            resultSb.append(byteToHex(byArr[i]));
        }
        return resultSb.toString();
    }

    /**
     * 字节转16进制
     *
     * @param b
     * @return java.lang.String
     * @throws
     */
    private static String byteToHex(byte b) {
        int n = b;
        if (n < 0) {
            n = 256 + n;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    /**
     * 合并内容和盐
     *
     * @param content
     * @param salt
     * @return java.lang.String
     */
    private static String mergeContentAndSalt(String content, String salt) {
        if (content == null) {
            content = "";
        }
        if (StringUtils.isBlank(salt)) {
            return content;
        } else {
            return content + "{" + salt + "}";
        }
    }

    /**
     * 获取加密盐
     *
     * @return java.lang.String
     */
    public static String getSalt() {
        return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 20);
    }

    /**
     * 密码匹配验证
     *
     * @param rawPass 明文
     * @param encPass 密文
     * @param algorithm 算法
     * @return boolean
     */
    public static boolean matches(String rawPass, String encPass, String algorithm) {
        return HashUtils.matches(rawPass, encPass, null, algorithm);
    }

    /**
     * 密码匹配验证
     *
     * @param rawPass 明文
     * @param encPass 密文
     * @param salt 盐
     * @param algorithm 算法
     * @return boolean
     */
    public static boolean matches(String rawPass, String encPass, String salt, String algorithm) {
        String pass1 = compute(rawPass, salt, algorithm);
        String pass2 = "" + encPass;
        return pass1.equals(pass2);
    }


}
