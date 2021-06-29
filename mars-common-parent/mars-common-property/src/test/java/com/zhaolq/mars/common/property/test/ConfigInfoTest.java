package com.zhaolq.mars.common.property.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

import javax.annotation.Resource;

/**
 *
 *
 * @author zhaolq
 * @date 2021/3/28 12:58
 */
@Slf4j
@SpringBootTest
@SpringBootApplication
public class ConfigInfoTest {

    @Resource
    private Environment env;
    @Resource
    private ConfigInfo1 configInfo1;
    @Resource
    private ConfigInfo2 configInfo2;

    /**
     * 注解@PropertySource可以将PropertySource添加到Spring的Environment
     * @see org.springframework.context.annotation.PropertySource
     */
    @Test
    public void getEnvironment() {
        System.out.println(env.getClass().getSimpleName());
        System.out.println(env.getProperty("test.config.info.username"));
        System.out.println(env.getProperty("test.config.info.password"));
        System.out.println(env.getProperty("test.config.info.map"));
        System.out.println(env.getProperty("test.config.info.list"));
        System.out.println(env.getProperty("test.config.info.host.ip"));
        System.out.println(env.getProperty("test.config.info.host.port"));
    }

    @Test
    public void getConfigInfo1() {
        // System.out.println(JacksonUtils.objectToJson(configInfo));
        // System.out.println(JSONUtil.parse(configInfo).toString());
        System.out.println("username:\t" + configInfo1.getUsername());
        System.out.println("password:\t" + configInfo1.getPassword());
        System.out.println("     map:\t" + configInfo1.getMap());
        System.out.println("    list:\t" + configInfo1.getList());

        System.out.println("test.config.info.username:\t" + System.getProperty("test.config.info.username"));
        System.out.println("JAVA_HOME:\t" + System.getenv("JAVA_HOME"));
    }

    @Test
    public void getConfigInfo2() {
        // System.out.println(JacksonUtils.objectToJson(configInfo));
        // System.out.println(JSONUtil.parse(configInfo).toString());
        System.out.println("username:\t" + configInfo2.getUsername());
        System.out.println("password:\t" + configInfo2.getPassword());
        System.out.println("      ip:\t" + configInfo2.getHost().getIp());
        System.out.println("    port:\t" + configInfo2.getHost().getPort());
        System.out.println("     map:\t" + configInfo2.getMap());
        System.out.println("    list:\t" + configInfo2.getList());

        System.out.println("test.config.info.username:\t" + System.getProperty("test.config.info.username"));
        System.out.println("JAVA_HOME:\t" + System.getenv("JAVA_HOME"));
    }

}
