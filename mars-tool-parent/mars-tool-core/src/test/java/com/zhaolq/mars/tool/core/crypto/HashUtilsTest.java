package com.zhaolq.mars.tool.core.crypto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 *
 * @author zhaolq
 * @date 2021/5/11 17:34
 */
public class HashUtilsTest {

    static {
    }

    @BeforeEach
    public void before() {

    }

    @Test
    public void test() {
        System.out.println("888888的MD5值：" + HashUtils.compute("888888", HashUtils.MD5));
        System.out.println(HashUtils.matches("888888", "21218CCA77804D2BA1922C33E0151105", HashUtils.MD5));

        /* SHA-1 */

        System.out.println("888888的SHA-1值：" + HashUtils.compute("888888", HashUtils.SHA_1));
        System.out.println(HashUtils.matches("888888", "1F82C942BEFDA29B6ED487A51DA199F78FCE7F05", HashUtils.SHA_1));

        /* SHA-2 */

        System.out.println("888888的SHA-224值：" + HashUtils.compute("888888", HashUtils.SHA_224));
        System.out.println(HashUtils.matches("888888", "41189B5DEF603C905842034C68B30A2415D38B16219267557998E3A4", HashUtils.SHA_224));

        System.out.println("888888的SHA-256值：" + HashUtils.compute("888888", HashUtils.SHA_256));
        System.out.println(HashUtils.matches("888888", "92925488B28AB12584AC8FCAA8A27A0F497B2C62940C8F4FBC8EF19EBC87C43E", HashUtils.SHA_256));

        System.out.println("888888的SHA-384值：" + HashUtils.compute("888888", HashUtils.SHA_384));
        System.out.println(HashUtils.matches("888888",
                "1785FF431B997F933641E5A2F41D67F0B247492F3981CDA988A32649FDF29E4485858B9A24C07E35608542C5EA767D99",
                HashUtils.SHA_384));

        System.out.println("888888的SHA-512值：" + HashUtils.compute("888888", HashUtils.SHA_512));
        System.out.println(HashUtils.matches("888888",
                "E4AED93EC8E0084EDAEF0CB945AA5ACB885792DEA7C115F6DE9A96C77DF0CA617761738C76E965F18AAFC30ECCBE0DACC9EC1788A7A1BBC0D6EF59B98047C099",
                HashUtils.SHA_512));

        System.out.println("888888的SHA-512/224值：" + HashUtils.compute("888888", HashUtils.SHA_512_224));
        System.out.println(HashUtils.matches("888888", "4FCEAD230CA076C7E77728EF2F5093CA44B8C3EDE92B910E62BF1A0A", HashUtils.SHA_512_224));

        System.out.println("888888的SHA_512/256值：" + HashUtils.compute("888888", HashUtils.SHA_512_256));
        System.out.println(HashUtils.matches("888888", "474F684B6A317558AA1983D61AD629A0FE5B040C8E79B8F27D847110AC3A0788", HashUtils.SHA_512_256));

        /* SHA-3 */

        System.out.println("888888的SHA3-224值：" + HashUtils.compute("888888", HashUtils.SHA3_224));
        System.out.println(HashUtils.matches("888888", "5CDF8483D4FB9CB6986DD13F963CBC0106E579FC0606420D00F5398A", HashUtils.SHA3_224));

        System.out.println("888888的SHA3-256值：" + HashUtils.compute("888888", HashUtils.SHA3_256));
        System.out.println(HashUtils.matches("888888", "CF638199274611CF1B8FEC5FC2A1872BB5E173AE555FE3B4E9FACF4A24646D6F", HashUtils.SHA3_256));

        System.out.println("888888的SHA3-384值：" + HashUtils.compute("888888", HashUtils.SHA3_384));
        System.out.println(HashUtils.matches("888888",
                "2D8ED3EBE567D0B7129DEAF63678DC2408558650993EA36DDD5CAAF306284858D1CB82482D403A2873CC0F393F6A97D2",
                HashUtils.SHA3_384));

        System.out.println("888888的SHA3-512值：" + HashUtils.compute("888888", HashUtils.SHA3_512));
        System.out.println(HashUtils.matches("888888",
                "9B3D9AE90816E35FE0846AD8C3A588962AE06B311BB261719E5FA3F4E58DB8A2CEF0F488B40EEBE64FADD10609532A55FA8F2DC2CC4E76867E1088B99C2AF820",
                HashUtils.SHA3_512));
    }
}