package com.zhaolq.mars.common.mq.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.List;
import java.util.Map;

/**
 * 获取mq配置信息
 *
 * @PropertySource能从父模块中读取资源文件(仅支持.properties和.xml格式)。
 * @ConfigurationProperties注解：将 *.properties 和 application.yml(.yml不可自定义) 配置文件属性转化为bean对象。配合spring-boot-configuration-processor依赖，可以生成
 * 元数据，这样在输入时就会给出可用的属性提示，属性提示仅在 application.yml 和 application.properties 出现，自定义的文件不会有属性提示，如：mq.properties。
 *
 * 友情提示: 尽量使用 application.yml 和 application.properties
 *
 *     区别                  @ConfigurationProperties                @Value
 *     功能	                批量注入配置文件的属性	                    一个个指定
 *     松散绑定(松散语法)      支持                                    不支持
 *     spEl表达式	        不支持	                                支持
 *     JSR303数据校验	    支持	                                    不支持
 *     复杂类型封装           各种复杂类型属性(Map、List、内部类)         支持简单属性(Map、List)
 *
 * @author zhaolq
 * @date 2021/3/28 12:44
 */
@Data
@Configuration
@PropertySource(value = {"classpath:/mq.properties", "classpath:/mq.xml"}, encoding = "UTF-8", ignoreResourceNotFound = true)
@ConfigurationProperties(prefix = MqConfigInfo.PREFIX, ignoreInvalidFields = true)
public class MqConfigInfo {

    public static final String PREFIX = "custom.mq";

    /**
     * 支持@Value("${custom.mq.username}")
     */
    private String username;

    /**
     * 支持@Value("${custom.mq.password}")
     */
    private String password;

    /**
     * 不支持@Value
     */
    private Host host = new MqConfigInfo.Host();

    /**
     * 支持@Value("#{${custom.mq.map}}")
     */
    @Value("#{${custom.mq.map}}")
    private Map<String, String> map;

    /**
     * 不支持@Value
     */
    private Map<String, String> map2;

    /**
     * 支持@Value("#{'${custom.mq.list}'.split(',')}")
     */
    private List<String> list;

    /**
     * 不支持@Value
     */
    private List<String> list2;

    /**
     * 只有内部类才可以使用static修饰
     */
    @Data
    public static class Host {

        /**
         * 有缺省值
         */
        private String ip = "0.0.0.0";
        private int port = 0;

    }

}
