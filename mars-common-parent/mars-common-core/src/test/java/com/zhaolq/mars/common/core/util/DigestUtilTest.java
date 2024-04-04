package com.zhaolq.mars.common.core.util;

import org.junit.jupiter.api.Test;

/**
 *
 *
 * @Author zhaolq
 * @Date 2021/5/12 17:02
 */
public class DigestUtilTest {

    @Test
    public void test() {
        // System.out.println("888888的MD5值：" + DigestUtils.md5Hex("888888".getBytes()).toUpperCase());

        System.out.println("888888的MD5值：" + DigestUtil.compute("888888", DigestUtil.MD5));
        System.out.println(DigestUtil.matches("888888", "21218CCA77804D2BA1922C33E0151105", DigestUtil.MD5));

        /* SHA-1 */

        System.out.println("888888的SHA-1值：" + DigestUtil.compute("888888", DigestUtil.SHA_1));
        System.out.println(DigestUtil.matches("888888", "1F82C942BEFDA29B6ED487A51DA199F78FCE7F05", DigestUtil.SHA_1));

        /* SHA-2 */

        System.out.println("888888的SHA-224值：" + DigestUtil.compute("888888", DigestUtil.SHA_224));
        System.out.println(DigestUtil.matches("888888", "41189B5DEF603C905842034C68B30A2415D38B16219267557998E3A4", DigestUtil.SHA_224));

        System.out.println("888888的SHA-256值：" + DigestUtil.compute("888888", DigestUtil.SHA_256));
        System.out.println(DigestUtil.matches("888888", "92925488B28AB12584AC8FCAA8A27A0F497B2C62940C8F4FBC8EF19EBC87C43E", DigestUtil.SHA_256));

        System.out.println("888888的SHA-384值：" + DigestUtil.compute("888888", DigestUtil.SHA_384));
        System.out.println(DigestUtil.matches("888888",
                "1785FF431B997F933641E5A2F41D67F0B247492F3981CDA988A32649FDF29E4485858B9A24C07E35608542C5EA767D99",
                DigestUtil.SHA_384));

        System.out.println("888888的SHA-512值：" + DigestUtil.compute("888888", DigestUtil.SHA_512));
        System.out.println(DigestUtil.matches("888888",
                "E4AED93EC8E0084EDAEF0CB945AA5ACB885792DEA7C115F6DE9A96C77DF0CA617761738C76E965F18AAFC30ECCBE0DACC9EC1788A7A1BBC0D6EF59B98047C099",
                DigestUtil.SHA_512));

        System.out.println("888888的SHA-512/224值：" + DigestUtil.compute("888888", DigestUtil.SHA_512_224));
        System.out.println(DigestUtil.matches("888888", "4FCEAD230CA076C7E77728EF2F5093CA44B8C3EDE92B910E62BF1A0A", DigestUtil.SHA_512_224));

        System.out.println("888888的SHA_512/256值：" + DigestUtil.compute("888888", DigestUtil.SHA_512_256));
        System.out.println(DigestUtil.matches("888888", "474F684B6A317558AA1983D61AD629A0FE5B040C8E79B8F27D847110AC3A0788",
                DigestUtil.SHA_512_256));

        /* SHA-3 */

        System.out.println("888888的SHA3-224值：" + DigestUtil.compute("888888", DigestUtil.SHA3_224));
        System.out.println(DigestUtil.matches("888888", "5CDF8483D4FB9CB6986DD13F963CBC0106E579FC0606420D00F5398A", DigestUtil.SHA3_224));

        System.out.println("888888的SHA3-256值：" + DigestUtil.compute("888888", DigestUtil.SHA3_256));
        System.out.println(DigestUtil.matches("888888", "CF638199274611CF1B8FEC5FC2A1872BB5E173AE555FE3B4E9FACF4A24646D6F", DigestUtil.SHA3_256));

        System.out.println("888888的SHA3-384值：" + DigestUtil.compute("888888", DigestUtil.SHA3_384));
        System.out.println(DigestUtil.matches("888888",
                "2D8ED3EBE567D0B7129DEAF63678DC2408558650993EA36DDD5CAAF306284858D1CB82482D403A2873CC0F393F6A97D2",
                DigestUtil.SHA3_384));

        System.out.println("888888的SHA3-512值：" + DigestUtil.compute("888888", DigestUtil.SHA3_512));
        System.out.println(DigestUtil.matches("888888",
                "9B3D9AE90816E35FE0846AD8C3A588962AE06B311BB261719E5FA3F4E58DB8A2CEF0F488B40EEBE64FADD10609532A55FA8F2DC2CC4E76867E1088B99C2AF820",
                DigestUtil.SHA3_512));

    }

}
