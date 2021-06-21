package com.zhaolq.mars.common.log;

import lombok.Data;
import org.springframework.boot.logging.LogLevel;

/**
 * 记录器的配置
 *
 * @author zhaolq
 * @date 2021/6/21 11:05
 */
public class LoggerConfig {

    /**
     * 记录器的名称
     */
    private String loggerName;

    /**
     * 日志级别 @see LogLevel
     */
    private String level;

    public String getLoggerName() {
        return loggerName;
    }

    public void setLoggerName(String loggerName) {
        this.loggerName = loggerName;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
