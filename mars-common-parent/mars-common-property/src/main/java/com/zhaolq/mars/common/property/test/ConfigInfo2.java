package com.zhaolq.mars.common.property.test;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.support.DefaultPropertySourceFactory;

import java.util.List;
import java.util.Map;

/**
 * 获取自定义配置方式二：可以读取父模块配置文件
 *
 * @ConfigurationProperties注解： 可以根据一个前缀将配置文件的属性映射成一个POJO实体类。配合spring-boot-configuration-processor依赖，可以生成元数据，这样在输入时就会给出可用的属性提示，
 * 属性提示仅在 application.yml 和 application.properties 出现，自定义的文件不会有属性提示，如：test.properties。
 * <p>
 * 区别                  @ConfigurationProperties                @Value
 * 功能	                批量注入配置文件的属性	                    一个个指定
 * 松散绑定(松散语法)      支持                                    不支持
 * spEl表达式	        不支持	                                支持
 * JSR303数据校验	    支持	                                    不支持
 * 复杂类型封装           各种复杂类型属性(Map、List、内部类)         支持简单属性(Map、List)
 * @Author zhaolq
 * @Date 2021/6/10 13:53
 */
@Data
@Configuration
@PropertySource(value = {"classpath:/test.properties", "classpath:/test.properties"}, encoding = "UTF-8", ignoreResourceNotFound = true,
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
