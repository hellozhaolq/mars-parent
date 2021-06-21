package com.zhaolq.mars.common.log;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.logging.LogLevel;
import org.springframework.boot.logging.LoggerConfiguration;
import org.springframework.boot.logging.LoggingSystem;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.boot.logging.LoggingSystem.ROOT_LOGGER_NAME;

/**
 * 日志级别设置服务类
 * 提供动态修改日志级别的入口，方便用户动态调整日志级别
 *
 * https://tech.meituan.com/2017/02/17/change-log-level.html
 *
 * @author zhaolq
 * @date 2021/6/18 17:13
 */
@Component
@Slf4j
public class LoggerLevelSettingService {

    /**
     * LoggingSystem服务是SpringBoot对日志系统的抽象，是一个顶层的抽象类。他有很多具体的实现。
     *
     * 有了LoggingSystem以后，我们就可以通过他来动态的修改日志级别。他帮我们屏蔽掉了底层的具体日志框架。
     *
     * SpringBoot在启动时，会完成LoggingSystem的初始化，这部分代码是在LoggingApplicationListener类的onApplicationStartingEvent方法中实现的。
     */
    @Resource
    private LoggingSystem loggingSystem;


    /**
     * 修改根记录器（Root）的日志级别
     */
    public void setRootLoggerLevel(String level) {
        LoggerConfiguration loggerConfiguration = loggingSystem.getLoggerConfiguration(ROOT_LOGGER_NAME);
        if (loggerConfiguration == null) {
            if (log.isErrorEnabled()) {
                log.error("no loggerConfiguration with loggerName " + level);
            }
            return;
        }
        if (!supportLevels().contains(level)) {
            if (log.isErrorEnabled()) {
                log.error("current Level is not support : " + level);
            }
            return;
        }
        if (!loggerConfiguration.getEffectiveLevel().equals(LogLevel.valueOf(level))) {
            if (log.isInfoEnabled()) {
                log.info("setRootLoggerLevel success,old level is '" + loggerConfiguration.getEffectiveLevel() + "' , new level is '" + level + "'");
            }
            loggingSystem.setLogLevel(ROOT_LOGGER_NAME, LogLevel.valueOf(level));
        }
    }

    private List<String> supportLevels() {
        return loggingSystem.getSupportedLogLevels().stream().map(Enum::name).collect(Collectors.toList());
    }

    /**
     * 根据用户传入的LoggerConfig，修改指定的loggerName对应的loggerLevel。
     */
    public void setLoggerLevel(List<LoggerConfig> configList) {
        Optional.ofNullable(configList).orElse(Collections.emptyList()).forEach(config -> {
            LoggerConfiguration loggerConfiguration = loggingSystem.getLoggerConfiguration(config.getLoggerName());
            if (loggerConfiguration == null) {
                if (log.isErrorEnabled()) {
                    log.error("no loggerConfiguration with loggerName " + config.getLoggerName());
                }
                return;
            }
            if (!supportLevels().contains(config.getLevel())) {
                if (log.isErrorEnabled()) {
                    log.error("current Level is not support : " + config.getLevel());
                }
                return;
            }
            if (log.isInfoEnabled()) {
                log.info("setLoggerLevel success for logger '" + config.getLoggerName() + "' ,old level is '" + loggerConfiguration.getEffectiveLevel() + "' , new level is '" + config.getLevel() + "'");
            }
            loggingSystem.setLogLevel(config.getLoggerName(), LogLevel.valueOf(config.getLevel()));
        });
    }


    /**
     * 设置降级记录器级别
     */
    public void setDegradationLoggerLevel(String level) {
        LoggerConfiguration loggerConfiguration = loggingSystem.getLoggerConfiguration(this.getClass().getName());
        if (loggerConfiguration == null) {
            if (log.isWarnEnabled()) {
                log.warn("no loggerConfiguration with loggerName " + level);
            }
            return;
        }
        if (!supportLevels().contains(level)) {
            if (log.isErrorEnabled()) {
                log.error("current Level is not support : " + level);
            }
            return;
        }
        if (!loggerConfiguration.getEffectiveLevel().equals(LogLevel.valueOf(level))) {
            loggingSystem.setLogLevel(this.getClass().getName(), LogLevel.valueOf(level));
        }
    }

}
