package com.zhaolq.mars.common.property.server;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 *
 *
 * @author zhaolq
 * @date 2021/6/10 11:15
 */
@Component
public class ServerInfo {

    @Value("${server.port}")
    private String port;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    public String getPort() {
        return port;
    }

    public String getContextPath() {
        return contextPath;
    }

}
