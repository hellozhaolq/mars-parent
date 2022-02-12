package com.zhaolq.mars.common.property.test;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.support.DefaultPropertySourceFactory;

/**
 * 获取自定义配置方式一：可以读取父模块配置文件
 * <p>
 * 请习惯阅读源码，会有收获哦！
 *
 * @author zhaolq
 * @PropertySource注解 用于指定资源文件读取的位置，它不仅能读取properties文件，也能读取xml文件，并且通过YAML解析器解析YAML文件。
 * 默认的PropertySourceFactory实现只能解析application.yml，自定义实现参考
 * https://blog.csdn.net/qq_40837310/article/details/106587158。不配置，默认读取 application.yml 和 application.properties
 * @PropertySource注解 可以将PropertySource添加到Spring的Environmen。示例见 ConfigInfoTest。
 * <p>
 * 友情提示:
 * 尽量使用 application.yml 和 application.properties 做为配置文件。
 * 尽量使用.properties格式，为避免部署人员配置格式错误。
 * @date 2021/6/10 13:48
 */
@Configuration
@PropertySource(value = {"classpath:/test.properties", "classpath:/test.properties"}, encoding = "UTF-8",
        ignoreResourceNotFound = true, factory = DefaultPropertySourceFactory.class)
public class ConfigInfo1 {

    @Value("${test.defaultValue:10}")
    private Integer defaultValue;

    @Value("${test.config.info.username}")
    private String username;

    @Value("${test.config.info.password}")
    private String password;

    @Value("#{${test.config.info.map}}")
    private Map<String, String> map;

    @Value("#{'${test.config.info.list}'.split(',')}")
    private List<String> list;

    public Integer getDefaultValue() {
        return defaultValue;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Map<String, String> getMap() {
        return map;
    }

    public List<String> getList() {
        return list;
    }
}
