package com.zhaolq.mars.common.log;

import org.springframework.boot.logging.LogLevel;
import org.springframework.boot.logging.LoggerConfiguration;

import static org.springframework.boot.logging.LoggingSystem.ROOT_LOGGER_NAME;

/**
 * 记录器级别
 *
 * @author zhaolq
 * @date 2021/6/21 21:10
 */
public class LoggerLevel {
    /**
     * 记录器的名称（又叫 loggerName，packageName）
     */
    private String name;

    /**
     * 日志级别，用做接口参数
     *
     * @see org.springframework.boot.logging.LogLevel
     * @see org.apache.logging.log4j.Level
     */
    private String level;

    /**
     * 配置级别
     */
    private String configuredLevel;

    /**
     * 有效级别
     */
    private String effectiveLevel;

    public LoggerLevel() {
    }

    public LoggerLevel(LoggerConfiguration configuration) {
        LogLevel configuredLevel = configuration.getConfiguredLevel();
        this.configuredLevel = configuredLevel == null ? null : configuredLevel.name();
        LogLevel effectiveLevel = configuration.getEffectiveLevel();
        this.effectiveLevel = effectiveLevel == null ? null : effectiveLevel.name();
        this.name = configuration.getName();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (ROOT_LOGGER_NAME.equalsIgnoreCase(name)) {
            this.name = ROOT_LOGGER_NAME;
            return;
        }
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level.toUpperCase();
    }


    public String getConfiguredLevel() {
        return this.configuredLevel;
    }

    public String getEffectiveLevel() {
        return this.effectiveLevel;
    }
}
