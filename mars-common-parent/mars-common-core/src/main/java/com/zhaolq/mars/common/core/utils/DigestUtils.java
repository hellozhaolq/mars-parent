package com.zhaolq.mars.common.core.utils;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import com.zhaolq.mars.tool.core.utils.StringUtils;

/**
 * 摘要算法工具类
 *
 * @author zhaolq
 * @date 2021/5/12 17:00
 */
public final class DigestUtils {

    public static final String[] hexDigits = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};
    public static final String DEFAULT_CHARSET = "UTF8";

    public static final String MD5 = "MD5";

    /* SHA-1 被攻破 */

    public static final String SHA_1 = "SHA-1";

    /* SHA-2 */

    public static final String SHA_224 = "SHA-224";
    public static final String SHA_256 = "SHA-256";
    public static final String SHA_384 = "SHA-384";
    public static final String SHA_512 = "SHA-512";
    public static final String SHA_512_224 = "SHA-512/224";
    public static final String SHA_512_256 = "SHA-512/256";

    /* SHA-3 */

    public static final String SHA3_224 = "SHA3-224";
    public static final String SHA3_256 = "SHA3-256";
    public static final String SHA3_384 = "SHA3-384";
    public static final String SHA3_512 = "SHA3-512";
    // 可变长
    public static final String SHAKE128 = "SHAKE128";
    public static final String SHAKE256 = "SHAKE256";

    /**
     * 哈希计算
     *
     * @param rawText 明文
     * @return java.lang.String
     */
    public static String compute(String rawText) {
        return compute(rawText, MD5);
    }

    /**
     * 哈希计算
     *
     * @param rawText 明文
     * @param algorithm 算法
     * @return java.lang.String
     */
    public static String compute(String rawText, String algorithm) {
        return compute(rawText, Charset.forName(DEFAULT_CHARSET), algorithm);
    }

    /**
     * 哈希计算
     *
     * @param rawText 明文
     * @param charset 字符集
     * @param algorithm 算法
     * @return java.lang.String
     */
    public static String compute(String rawText, Charset charset, String algorithm) {
        return compute(rawText, charset, null, algorithm);
    }

    /**
     * 哈希计算
     *
     * @param rawText 明文
     * @param salt 盐
     * @param algorithm 算法
     * @return java.lang.String
     */
    public static String compute(String rawText, String salt, String algorithm) {
        return compute(rawText, Charset.forName(DEFAULT_CHARSET), salt, algorithm);
    }

    /**
     * 哈希计算
     *
     * @param rawText 明文
     * @param charset 字符集
     * @param salt 盐
     * @param algorithm 算法
     * @return java.lang.String
     */
    public static String compute(String rawText, Charset charset, String salt, String algorithm) {
        String result = null;
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            result = byteArrayToHex(md.digest(mergeRawTextAndSalt(rawText, salt).getBytes(charset))).toUpperCase();
        } catch (NoSuchAlgorithmException e) {
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
     * 字节数组转16进制字符串，和byteArrayToHex功能相同。
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
     * @param rawText
     * @param salt
     * @return java.lang.String
     */
    private static String mergeRawTextAndSalt(String rawText, String salt) {
        if (rawText == null) {
            rawText = "";
        }
        if (StringUtils.isBlank(salt)) {
            return rawText;
        } else {
            return rawText + "{" + salt + "}";
        }
    }

    /**
     * 生成加密盐
     *
     * @return java.lang.String
     */
    public static String generateSalt() {
        return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 20);
    }

    /**
     * 是否匹配哈希值
     *
     * @param rawText 明文
     * @param encText 密文
     * @param algorithm 算法
     * @return boolean
     */
    public static boolean matches(String rawText, String encText, String algorithm) {
        return matches(rawText, encText, Charset.forName(DEFAULT_CHARSET), algorithm);
    }

    /**
     * 是否匹配哈希值
     *
     * @param rawText 明文
     * @param encText 密文
     * @param charset 字符集
     * @param algorithm 算法
     * @return boolean
     */
    public static boolean matches(String rawText, String encText, Charset charset, String algorithm) {
        return matches(rawText, encText, charset, null, algorithm);
    }

    /**
     * 是否匹配哈希值
     *
     * @param rawText 明文
     * @param encText 密文
     * @param salt 盐
     * @param algorithm 算法
     * @return boolean
     */
    public static boolean matches(String rawText, String encText, String salt, String algorithm) {
        return matches(rawText, encText, Charset.forName(DEFAULT_CHARSET), salt, algorithm);
    }

    /**
     * 是否匹配哈希值
     *
     * @param rawText 明文
     * @param encText 密文
     * @param charset 字符集
     * @param salt 盐
     * @param algorithm 算法
     * @return boolean
     */
    public static boolean matches(String rawText, String encText, Charset charset, String salt, String algorithm) {
        String pass1 = compute(rawText, charset, salt, algorithm);
        String pass2 = "" + encText;
        return pass1.equals(pass2);
    }
}
