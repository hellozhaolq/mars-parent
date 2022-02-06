package com.zhaolq.mars.common.springboot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.function.Function;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.SimpleCommandLinePropertySource;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.env.SystemEnvironmentPropertySource;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * @author zhaolq
 * @since 2021/6/23 17:48
 */
public class MarsApplication {

    public static ConfigurableApplicationContext run(Class<?> primarySource, String... args) {
        ConfigurableApplicationContext context = SpringApplication.run(primarySource, args);

        // 这里可以对context做些事情，尽量不重写SpringApplication.run

        return context;
    }


    public static ConfigurableApplicationContext run(String appName, Class source, String... args) {
        SpringApplicationBuilder builder = createSpringApplicationBuilder(appName, source, args);
        return builder.run(args);
    }

    public static SpringApplicationBuilder createSpringApplicationBuilder(
            String appName, Class source, String... args) {
        Assert.hasText(appName, "[appName]服务名不能为空");
        ConfigurableEnvironment environment = new StandardEnvironment();
        MutablePropertySources propertySources = environment.getPropertySources();
        propertySources.addFirst(new SimpleCommandLinePropertySource(args));
        propertySources.addLast(new MapPropertySource("systemProperties", environment.getSystemProperties()));
        propertySources.addLast(new SystemEnvironmentPropertySource("systemEnvironment",
                environment.getSystemEnvironment()));
        String[] activeProfiles = environment.getActiveProfiles();
        List<String> profiles = Arrays.asList(activeProfiles);
        List<String> presetProfiles = new ArrayList(Arrays.asList("dev", "test", "prod"));
        presetProfiles.retainAll(profiles);
        List<String> activeProfileList = new ArrayList(profiles);
        Function<Object[], String> joinFun = StringUtils::arrayToCommaDelimitedString;
        SpringApplicationBuilder builder = new SpringApplicationBuilder(new Class[]{source});
        String profile;
        if (activeProfileList.isEmpty()) {
            profile = "dev";
            activeProfileList.add(profile);
            builder.profiles(new String[]{profile});
        } else {
            if (activeProfileList.size() != 1) {
                throw new RuntimeException("同时存在环境变量:[" + StringUtils.arrayToCommaDelimitedString(activeProfiles) +
                        "]");
            }

            profile = (String) activeProfileList.get(0);
        }

        String startJarPath = MarsApplication.class.getResource("/").getPath().split("!")[0];
        String activePros = (String) joinFun.apply(activeProfileList.toArray());
        System.out.println(String.format("----启动中，读取到的环境变量:[%s]，jar地址:[%s]----", activePros, startJarPath));
        Properties props = System.getProperties();
        props.setProperty("spring.application.name", appName);
        props.setProperty("spring.profiles.active", profile);
        props.setProperty("info.version", "1.0.0");
        props.setProperty("info.desc", appName);
        props.setProperty("blade.env", profile);
        props.setProperty("blade.name", appName);
        props.setProperty("blade.is-local", String.valueOf(isLocalDev()));
        props.setProperty("blade.dev-mode", profile.equals("prod") ? "false" : "true");
        props.setProperty("blade.service.version", "1.0.0");
        props.setProperty("spring.main.allow-bean-definition-overriding", "true");
        props.setProperty("spring.cloud.nacos.config.prefix", "blade");
        props.setProperty("spring.cloud.nacos.config.file-extension", "yaml");
        props.setProperty("spring.cloud.sentinel.transport.dashboard", "127.0.0.1:8858");
        return builder;
    }

    public static boolean isLocalDev() {
        String osName = System.getProperty("os.name");
        return StringUtils.hasText(osName) && !"LINUX".equals(osName.toUpperCase());
    }

}
