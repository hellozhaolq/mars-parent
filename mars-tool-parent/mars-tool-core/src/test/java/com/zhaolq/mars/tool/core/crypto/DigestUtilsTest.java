package com.zhaolq.mars.tool.core.crypto;

import cn.hutool.crypto.SecureUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 *
 * @author zhaolq
 * @date 2021/5/12 17:02
 */
public class DigestUtilsTest {

    static {
    }

    @BeforeEach
    public void before() {

    }

    @Test
    public void test() {
        System.out.println("888888的MD5值：" + DigestUtils.md5Hex("888888".getBytes()).toUpperCase());


    }
}
