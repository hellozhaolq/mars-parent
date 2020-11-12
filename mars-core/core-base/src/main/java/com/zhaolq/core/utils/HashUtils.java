package com.zhaolq.core.utils;

import java.nio.charset.Charset;
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

    // public static void main(String[] args) {
    //     System.out.println("888888的MD5值：" + compute("888888", HashUtils.MD5));
    //     System.out.println(matches("888888", "21218CCA77804D2BA1922C33E0151105", HashUtils.MD5));
    //
    //     /* SHA-1 */
    //
    //     System.out.println("888888的SHA-1值：" + compute("888888", HashUtils.SHA_1));
    //     System.out.println(matches("888888", "1F82C942BEFDA29B6ED487A51DA199F78FCE7F05", HashUtils.SHA_1));
    //
    //     /* SHA-2 */
    //
    //     System.out.println("888888的SHA-224值：" + compute("888888", HashUtils.SHA_224));
    //     System.out.println(matches("888888", "41189B5DEF603C905842034C68B30A2415D38B16219267557998E3A4", HashUtils.SHA_224));
    //
    //     System.out.println("888888的SHA-256值：" + compute("888888", HashUtils.SHA_256));
    //     System.out.println(matches("888888", "92925488B28AB12584AC8FCAA8A27A0F497B2C62940C8F4FBC8EF19EBC87C43E", HashUtils.SHA_256));
    //
    //     System.out.println("888888的SHA-384值：" + compute("888888", HashUtils.SHA_384));
    //     System.out.println(matches("888888", "1785FF431B997F933641E5A2F41D67F0B247492F3981CDA988A32649FDF29E4485858B9A24C07E35608542C5EA767D99",
    //             HashUtils.SHA_384));
    //
    //     System.out.println("888888的SHA-512值：" + compute("888888", HashUtils.SHA_512));
    //     System.out.println(matches("888888",
    //             "E4AED93EC8E0084EDAEF0CB945AA5ACB885792DEA7C115F6DE9A96C77DF0CA617761738C76E965F18AAFC30ECCBE0DACC9EC1788A7A1BBC0D6EF59B98047C099",
    //             HashUtils.SHA_512));
    //
    //     System.out.println("888888的SHA-512/224值：" + compute("888888", HashUtils.SHA_512_224));
    //     System.out.println(matches("888888", "4FCEAD230CA076C7E77728EF2F5093CA44B8C3EDE92B910E62BF1A0A", HashUtils.SHA_512_224));
    //
    //     System.out.println("888888的SHA_512/256值：" + compute("888888", HashUtils.SHA_512_256));
    //     System.out.println(matches("888888", "474F684B6A317558AA1983D61AD629A0FE5B040C8E79B8F27D847110AC3A0788", HashUtils.SHA_512_256));
    //
    //     /* SHA-3 */
    //
    //     System.out.println("888888的SHA3-224值：" + compute("888888", HashUtils.SHA3_224));
    //     System.out.println(matches("888888", "5CDF8483D4FB9CB6986DD13F963CBC0106E579FC0606420D00F5398A", HashUtils.SHA3_224));
    //
    //     System.out.println("888888的SHA3-256值：" + compute("888888", HashUtils.SHA3_256));
    //     System.out.println(matches("888888", "CF638199274611CF1B8FEC5FC2A1872BB5E173AE555FE3B4E9FACF4A24646D6F", HashUtils.SHA3_256));
    //
    //     System.out.println("888888的SHA3-384值：" + compute("888888", HashUtils.SHA3_384));
    //     System.out.println(matches("888888", "2D8ED3EBE567D0B7129DEAF63678DC2408558650993EA36DDD5CAAF306284858D1CB82482D403A2873CC0F393F6A97D2",
    //             HashUtils.SHA3_384));
    //
    //     System.out.println("888888的SHA3-512值：" + compute("888888", HashUtils.SHA3_512));
    //     System.out.println(matches("888888",
    //             "9B3D9AE90816E35FE0846AD8C3A588962AE06B311BB261719E5FA3F4E58DB8A2CEF0F488B40EEBE64FADD10609532A55FA8F2DC2CC4E76867E1088B99C2AF820",
    //             HashUtils.SHA3_512));
    // }

}
