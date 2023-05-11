package com.zhaolq.mars.common.log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.logging.LogLevel;
import org.springframework.boot.logging.LoggingSystem;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

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

    @Value("${logging.level.root}")
    private String rootLogLevel;

    @Value("${logging.level.com.zhaolq}")
    private String marsLogLevel;

    @Resource
    private LoggingSystem loggingSystem;

    /**
     * 项目统一包名前缀，也是记录器名称
     */
    public static final String SCHEMA = "com.zhaolq";

    @Override
    public void run(ApplicationArguments args) {
        log.trace(">>>>>>>> Initialize the logger level start <<<<<<<<");
        List<LoggerLevel> loggerLevelList = new ArrayList<>();

        // 根记录器级别
        LoggerLevel rootLoggerLevel = new LoggerLevel();
        rootLoggerLevel.setName(ROOT_LOGGER_NAME);
        rootLoggerLevel.setLevel(EnumUtils.getEnumIgnoreCase(LogLevel.class, rootLogLevel, LogLevel.INFO).name());
        loggerLevelList.add(rootLoggerLevel);

        // com.zhaolq记录器级别
        LoggerLevel projectLoggerLevel = new LoggerLevel();
        projectLoggerLevel.setName(SCHEMA);
        projectLoggerLevel.setLevel(EnumUtils.getEnumIgnoreCase(LogLevel.class, marsLogLevel, LogLevel.INFO).name());
        loggerLevelList.add(projectLoggerLevel);

        Optional.ofNullable(loggerLevelList).orElse(Collections.emptyList()).forEach(loggerLevel -> {
            this.loggingSystem.setLogLevel(loggerLevel.getName(), LogLevel.valueOf(loggerLevel.getLevel()));
            String msg = StringUtils.join("Logger Level >>>>>>>> ", loggerLevel.getName(), ": ", loggerLevel.getLevel(), "<<<<<<<<");
            log.info(msg);
        });
        log.trace(">>>>>>>> Initialize the logger level end <<<<<<<<");
    }
}
