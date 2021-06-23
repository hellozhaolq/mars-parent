package com.zhaolq.mars.common.log;

import cn.hutool.log.level.Level;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.logging.LogLevel;
import org.springframework.boot.logging.LoggingSystem;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.springframework.boot.logging.LoggingSystem.ROOT_LOGGER_NAME;

/**
 * 初始化记录器级别
 *
 * @author zhaolq
 * @date 2021/6/23 14:29
 */
@Component
@Slf4j
@Order(Ordered.LOWEST_PRECEDENCE)
public class InitLoggerLevelRunner implements ApplicationRunner {

    @Resource
    private LoggingSystem loggingSystem;

    /**
     * 项目统一包名前缀，也是记录器名称
     */
    public static final String SCHEMA = "com.zhaolq";

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info(">>>>>>>> Initialize the logger level <<<<<<<<");

        List<LoggerLevel> loggerLevelList = new ArrayList<>();

        // 跟记录器级别
        LoggerLevel rootLoggerLevel = new LoggerLevel();
        rootLoggerLevel.setName(ROOT_LOGGER_NAME);
        rootLoggerLevel.setLevel(Level.ERROR.name());
        loggerLevelList.add(rootLoggerLevel);

        // 项目记录器级别
        LoggerLevel projectLoggerLevel = new LoggerLevel();
        projectLoggerLevel.setName(SCHEMA);
        projectLoggerLevel.setLevel(Level.ERROR.name());
        loggerLevelList.add(projectLoggerLevel);

        Optional.ofNullable(loggerLevelList).orElse(Collections.emptyList()).forEach(loggerLevel -> {
            this.loggingSystem.setLogLevel(loggerLevel.getName(), LogLevel.valueOf(loggerLevel.getLevel()));
        });
    }
}
