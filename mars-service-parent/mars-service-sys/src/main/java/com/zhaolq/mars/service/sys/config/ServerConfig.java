package com.zhaolq.mars.service.sys.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.List;
import java.util.Map;

/**
 * 获取.properties或.yml中指定前缀的配置信息，可以嵌套
 * @ConfigurationProperties注解：将properties和yml配置文件属性转化为bean对象使用
 *
 *     区别                @ConfigurationProperties              @Value
 *     类型	                各种复制类型属性Map、内部类	           只支持简单属性
 * spEl表达式	                  不支持	                           支持
 * JSR303数据校验	               支持	                          不支持
 *     功能	                一个列属性批量注入	                     单属性注入
 *
 * @author zhaolq
 * @date 2021/3/26 9:31
 */
@Data
@Configuration
@PropertySource(value = "classpath:/application.properties", encoding = "UTF-8")
@ConfigurationProperties(prefix = ServerConfig.PREFIX, ignoreInvalidFields = true)
public class ServerConfig {

    public static final String PREFIX = "custom.server";

    /**
     * 无缺省值
     */
    private String username;
    private String password;

    /**
     * 有缺省值
     */
    private Host host = new ServerConfig.Host();

    private Map<String, String> map;
    private Map<String, String> map2;

    private List<String> list;
    private List<String> list2;

    /**
     * 只有内部类才可以使用static修饰
     */
    @Data
    public static class Host {

        private String ip = "192.168.0.3";

        private int port = 10000;

    }

}
