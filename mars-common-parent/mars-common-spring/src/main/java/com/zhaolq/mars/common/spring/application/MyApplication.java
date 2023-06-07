package com.zhaolq.mars.common.spring.application;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.SimpleCommandLinePropertySource;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.env.SystemEnvironmentPropertySource;
import org.springframework.util.Assert;

import com.zhaolq.mars.common.core.enums.OS;
import com.zhaolq.mars.common.core.enums.Profile;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zhaolq
 * @date 2021/6/23 17:48
 */
@Slf4j
public class MyApplication {
    /**
     * System.getProperties()和System.getenv()区别？
     * SystemProperties：系统属性。这里的系统指当前应用程序，
     * SystemEnvironment：操作系统环境变量。
     * <p>
     * 虽然两者本质都是map，提供字符串键和值，但有一些不同：
     * 1、属性可以在运行时更新。环境变量是操作系统变量的不可变副本，不能修改。
     * 2、属性仅在java平台中有效。环境变量是全局的，属于操作系统级——运行在同一台机器上的所有应用共用。
     * 3、当打包应用时，属性必须存在，但环境变量可以在任何时候创建。
     * 4、另外getProperty遵循骆驼命名约定，而getenv不是。
     */
    private static final String SYSTEM_PROPERTIES = "systemProperties";
    private static final String SYSTEM_ENVIRONMENT = "systemEnvironment";
    private static final String CUSTOMIZE_PROPERTIES = "customizeProperties";

    public static void run(String serviceName, Class<?> primarySource, String... args) {
        log.info(">>>>>>>> 系统启动中...");
        log.info("用户当前工作路径: " + System.getProperty("user.dir"));
        log.info("classpath路径: " + primarySource.getClassLoader().getResource("").getPath());
        SpringApplicationBuilder builder = new SpringApplicationBuilder(new Class<?>[]{primarySource});
        ConfigurableApplicationContext context = builder.headless(false).run(args);
        log.info(">>>>>>>> 系统启动成功");
    }

    /**
     * 此方法不推荐使用，系统初始化工作尽量放到bean加载完成后执行。
     *
     * @param serviceName serviceName
     * @param primarySource primarySource
     * @param args args
     * @return org.springframework.boot.builder.SpringApplicationBuilder
     */
    public static SpringApplicationBuilder createSpringApplicationBuilder(
            String serviceName, Class primarySource, String... args) {
        Assert.hasText(serviceName, "服务名不能为空");

        try {
            // 加载配置文件，设置或更新键值，用做系统属性
            System.getProperties().load(primarySource.getClassLoader().getResourceAsStream("application.properties"));
        } catch (IOException e) {
            log.error(e.getMessage());
        }

        ConfigurableEnvironment environment = new StandardEnvironment();

        // 激活的配置文件列表，转换为linkedlist（保存原来的顺序），Arrays.asList返回的集合无法add和remove
        Set<String> activeProfiles = new LinkedHashSet<>(Arrays.asList(environment.getActiveProfiles()));
        // 保留在指定集合中存在的元素，或者说删除所有不包含在指定集合中的元素。如果集合因调用而更改，则为true。
        activeProfiles.retainAll(Profile.allowedProfiles());

        String profile;
        if (activeProfiles.isEmpty()) {
            profile = Profile.DEV.getValue();
            activeProfiles.add(profile);
        } else {
            // 获取第一个元素
            profile = activeProfiles.iterator().next();
            if (activeProfiles.size() != 1) {
                log.info("存在多个环境变量: " + Arrays.toString(activeProfiles.toArray()));
            }
        }

        Properties properties = System.getProperties();
        properties.setProperty("spring.application.name", serviceName);
        properties.setProperty("spring.profiles.active", profile);
        properties.setProperty("spring.main.allow-bean-definition-overriding", "true");
        properties.setProperty("spring.cloud.nacos.config.prefix", serviceName);
        properties.setProperty("spring.cloud.nacos.config.file-extension", "yaml");
        properties.setProperty("spring.cloud.sentinel.transport.dashboard", "127.0.0.1:8858");
        properties.setProperty("mars.os-type", judgeSystem());
        properties.setProperty("mars.service.version", "1.0.0-deluxe");

        PropertySource<?> customizeProperties = new PropertiesPropertySource(CUSTOMIZE_PROPERTIES, properties);
        // MapPropertySource子类比较少，可以从名称上区分其功能
        PropertySource<?> systemProperties = new MapPropertySource(SYSTEM_PROPERTIES, environment.getSystemProperties());
        PropertySource<?> systemEnvironment = new SystemEnvironmentPropertySource(SYSTEM_ENVIRONMENT, environment.getSystemEnvironment());

        MutablePropertySources propertySources = environment.getPropertySources();
        // 将命令行参数添加到属性
        propertySources.addFirst(new SimpleCommandLinePropertySource(args));
        propertySources.addLast(systemProperties);
        // systemEnvironment优先级在systemProperties之后
        propertySources.addAfter(SYSTEM_PROPERTIES, systemEnvironment);
        propertySources.addLast(customizeProperties);


        SpringApplicationBuilder builder = new SpringApplicationBuilder(new Class<?>[]{primarySource});
        builder.profiles(new String[]{profile});
        return builder;
    }

    public static boolean isLinux() {
        return System.getProperty("os.name").toLowerCase(Locale.ROOT).contains(OS.LINUX.getValue());
    }

    public static boolean isWindows() {
        return System.getProperty("os.name").toLowerCase(Locale.ROOT).contains(OS.WINDOWS.getValue());
    }

    public static String judgeSystem() {
        if (isLinux()) {
            return OS.LINUX.getValue();
        } else if (isWindows()) {
            return OS.WINDOWS.getValue();
        } else {
            return "other";
        }
    }
}
