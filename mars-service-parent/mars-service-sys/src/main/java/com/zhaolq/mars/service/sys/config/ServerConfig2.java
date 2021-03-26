package com.zhaolq.mars.service.sys.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.List;
import java.util.Map;

/**
 *
 *
 * @author zhaolq
 * @date 2021/3/26 15:06
 */
@Data
@PropertySource(value = "classpath:/application.properties", encoding = "UTF-8")
@Configuration
public class ServerConfig2 {

    @Value("${custom.server.username}")
    private String username;

    @Value("${custom.server.password}")
    private String password;


    @Value("#{${custom.server.map}}")
    private Map<String, String> map;
    private Map<String, String> map2;

    @Value("#{'${custom.server.list}'.split(',')}")
    private List<String> list;
    private List<String> list2;

}
