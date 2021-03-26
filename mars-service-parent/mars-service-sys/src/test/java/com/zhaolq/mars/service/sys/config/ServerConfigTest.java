package com.zhaolq.mars.service.sys.config;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 *
 * @author zhaolq
 * @date 2021/3/26 14:54
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ServerConfigTest {

    @Autowired
    private ServerConfig serverConfig;
    @Autowired
    private ServerConfig2 serverConfig2;

    /**
     * 获取自定义Server配置信息
     */
    @Test
    public void getServerConfig() {
        System.out.println(JSONUtil.parse(serverConfig).toString());
        System.out.println("username:\t" + serverConfig.getUsername());
        System.out.println("password:\t" + serverConfig.getPassword());
        System.out.println("      ip:\t" + serverConfig.getHost().getIp());
        System.out.println("    port:\t" + serverConfig.getHost().getPort());
        System.out.println("     map:\t" + serverConfig.getMap());
        System.out.println("    map2:\t" + serverConfig.getMap2());
        System.out.println("    list:\t" + serverConfig.getList());
        System.out.println("   list2:\t" + serverConfig.getList2());

        System.out.println("custom.server.username:\t" + System.getProperty("custom.server.username"));
        System.out.println("JAVA_HOME:\t" + System.getenv("JAVA_HOME"));
    }

    /**
     * 获取自定义Mail配置信息
     */
    @Test
    public void getServerConfig2() {
        System.out.println(JSONUtil.parse(serverConfig2).toString());
        System.out.println("username:\t" + serverConfig2.getUsername());
        System.out.println("password:\t" + serverConfig2.getPassword());
        // System.out.println("      ip:\t" + serverConfig2.getHost().getIp());
        // System.out.println("    port:\t" + serverConfig2.getHost().getPort());
        System.out.println("     map:\t" + serverConfig2.getMap());
        System.out.println("    map2:\t" + serverConfig2.getMap2());
        System.out.println("    list:\t" + serverConfig2.getList());
        System.out.println("   list2:\t" + serverConfig2.getList2());
    }


}
