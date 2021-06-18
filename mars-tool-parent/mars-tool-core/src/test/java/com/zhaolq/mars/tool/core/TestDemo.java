package com.zhaolq.mars.tool.core;

import com.zhaolq.mars.tool.core.crypto.DigestUtils;
import org.junit.jupiter.api.*;

/**
 * 多次运行观察输出时机
 *
 * @author zhaolq
 * @date 2021/6/18 10:44
 */
public class TestDemo {

    static {
    }

    @BeforeEach
    public void beforeEach() {
        System.out.println("----- @BeforeEach： -----");
    }

    @AfterEach
    public void afterEach() {
        System.out.println("----- @AfterEach -----");
    }

    @BeforeAll
    public static void beforeAll() {
        System.out.println("----- @BeforeAll -----");
    }

    @AfterAll
    public static void afterAll() {
        System.out.println("----- @AfterAll -----");
    }

    @Test
    public void test() {
        System.out.println("----- 第一个@Test -----");
    }

    @Test
    public void test2() {
        System.out.println("----- 第二个@Test -----");
    }

}