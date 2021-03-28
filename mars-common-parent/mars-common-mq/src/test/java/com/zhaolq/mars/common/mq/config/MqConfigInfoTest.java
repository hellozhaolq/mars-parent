package com.zhaolq.mars.common.mq.config;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 *
 * @author zhaolq
 * @date 2021/3/28 12:58
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
@SpringBootApplication
public class MqConfigInfoTest {

    @Autowired
    private MqConfigInfo mqConfigInfo;

    /**
     * 获取自定义Server配置信息
     */
    @Test
    public void getMqConfigInfo() {
        System.out.println(JSONUtil.parse(mqConfigInfo).toString());
        System.out.println("username:\t" + mqConfigInfo.getUsername());
        System.out.println("password:\t" + mqConfigInfo.getPassword());
        System.out.println("      ip:\t" + mqConfigInfo.getHost().getIp());
        System.out.println("    port:\t" + mqConfigInfo.getHost().getPort());
        System.out.println("     map:\t" + mqConfigInfo.getMap());
        System.out.println("    map2:\t" + mqConfigInfo.getMap2());
        System.out.println("    list:\t" + mqConfigInfo.getList());
        System.out.println("   list2:\t" + mqConfigInfo.getList2());

        System.out.println("custom.server.username:\t" + System.getProperty("custom.server.username"));
        System.out.println("JAVA_HOME:\t" + System.getenv("JAVA_HOME"));
    }



}
