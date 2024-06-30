package com.zhaolq.mars.common.property.test;

import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.support.DefaultPropertySourceFactory;

import lombok.Data;

/**
 * 获取自定义配置方式二：可以读取父模块配置文件
 * <p>
 * 推荐阅读：https://www.baeldung.com/configuration-properties-in-spring-boot
 * <p>
 * 注解@ConfigurationProperties： 可以根据一个前缀将配置文件的属性映射成一个POJO实体类。配合spring-boot-configuration-processor依赖，可以生成元数据，这样在输入时就会给出可用的属性提示，
 * <p>
 *
 * @Author zhaolq
 * @Date 2021/6/10 13:53
 */
@Data
@Configuration
@PropertySource(value = {"classpath:/test.properties", "classpath:/test.properties"},
        encoding = "UTF-8",
        ignoreResourceNotFound = true,
        factory = DefaultPropertySourceFactory.class)
@ConfigurationProperties(prefix = ConfigInfo2.PREFIX, ignoreInvalidFields = true)
public class ConfigInfo2 {
    public static final String PREFIX = "test.config.info";

    private String username;

    private String password;

    private Map<String, String> map;

    private List<String> list;

    private Host host = new Host();

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
