package com.zhaolq.mars.common.property.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;

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

    @Autowired
    private ConfigInfo configInfo;

    /**
     * 获取自定义消息队列配置信息
     */
    @Test
    public void getConfigInfo() {
        // System.out.println(JacksonUtils.objectToJson(configInfo));
        // System.out.println(JSONUtil.parse(configInfo).toString());
        System.out.println("username:\t" + configInfo.getUsername());
        System.out.println("password:\t" + configInfo.getPassword());
        System.out.println("      ip:\t" + configInfo.getHost().getIp());
        System.out.println("    port:\t" + configInfo.getHost().getPort());
        System.out.println("     map:\t" + configInfo.getMap());
        System.out.println("    map2:\t" + configInfo.getMap2());
        System.out.println("    list:\t" + configInfo.getList());
        System.out.println("   list2:\t" + configInfo.getList2());

        System.out.println("test.config.info.username:\t" + System.getProperty("test.config.info.username"));
        System.out.println("JAVA_HOME:\t" + System.getenv("JAVA_HOME"));
    }


}
