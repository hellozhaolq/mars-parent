package com.zhaolq.mars.common.log;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NavigableSet;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.logging.LogLevel;
import org.springframework.boot.logging.LoggerConfiguration;
import org.springframework.boot.logging.LoggingSystem;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zhaolq.mars.common.core.result.R;
import com.zhaolq.mars.common.core.result.ResultCode;

import lombok.extern.slf4j.Slf4j;

/**
 * 日志级别设置控制器
 * 需要登录拦截
 *
 * @author zhaolq
 * @date 2021/6/21 20:13
 */
@Slf4j
@RestController
@RequestMapping("/logger")
public class LoggerLevelController {
    /**
     * LoggingSystem服务是SpringBoot对日志系统的抽象，是一个顶层的抽象类。它有很多具体的实现。
     * 有了LoggingSystem以后，我们就可以通过它来动态的修改日志级别。它帮我们屏蔽掉了底层的具体日志框架。
     * SpringBoot在启动时，会完成LoggingSystem的初始化，这部分代码是在LoggingApplicationListener类的onApplicationStartingEvent方法中实现的。
     */
    @Resource
    private LoggingSystem loggingSystem;

    @PostConstruct
    public void initLoggerLevel() {
        /**
         * @see com.zhaolq.mars.common.log.InitLoggerLevelRunner
         */
    }

    @GetMapping("/getLoggerLevel")
    public LoggerLevel getLoggerLevel(LoggerLevel loggerLevel) {
        Assert.notNull(loggerLevel, "Name must not be null");
        Assert.notNull(loggerLevel.getName(), "Name must not be null");
        LoggerConfiguration configuration = null;
        // 根记录器(ROOT)没有name属性，默认为空字符串，所以单独特殊处理。
        if ("ROOT".equalsIgnoreCase(loggerLevel.getName()) || "".equals(loggerLevel.getName().trim())) {
            configuration = this.loggingSystem.getLoggerConfiguration("");
        } else {
            configuration = this.loggingSystem.getLoggerConfiguration(loggerLevel.getName());
        }
        return configuration == null ? new LoggerLevel() : new LoggerLevel(configuration);
    }

    @GetMapping("/getLoggerLevelAll")
    public Map<String, Object> getLoggerLevelAll() {
        Collection<LoggerConfiguration> configurations = this.loggingSystem.getLoggerConfigurations();
        if (configurations == null) {
            return Collections.emptyMap();
        }
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("supportLevels", getSupportLevels());
        result.put("loggers", getLoggers(configurations));
        return result;
    }

    private Collection<LoggerLevel> getLoggers(Collection<LoggerConfiguration> configurations) {
        Map<String, LoggerLevel> loggers = new LinkedHashMap<>(configurations.size());
        for (LoggerConfiguration configuration : configurations) {
            loggers.put(configuration.getName(), new LoggerLevel(configuration));
        }
        return loggers.values();
    }

    @PostMapping("/setLoggerLevel")
    public void setLoggerLevel(@RequestBody(required = false) LoggerLevel loggerLevel) {
        Assert.notNull(loggerLevel, ResultCode.PARAM_NOT_COMPLETE.getDescCh());
        if (StringUtils.isBlank(loggerLevel.getName())) {
            new IllegalArgumentException("Name must not be empty");
        }
        this.loggingSystem.setLogLevel(loggerLevel.getName(), LogLevel.valueOf(loggerLevel.getLevel()));
    }

    @PostMapping("/setLoggerLevelBatch")
    public void setLoggerLevelBatch(@RequestBody(required = false) List<LoggerLevel> loggerLevelList) {
        Optional.ofNullable(loggerLevelList).orElse(Collections.emptyList()).forEach(loggerLevel -> {
            this.loggingSystem.setLogLevel(loggerLevel.getName(), LogLevel.valueOf(loggerLevel.getLevel()));
        });
    }

    private NavigableSet<LogLevel> getSupportLevels() {
        Set<LogLevel> set = this.loggingSystem.getSupportedLogLevels();
        // return set.stream().map(Enum::name).collect(Collectors.toList()); // 返回 List<String>
        return new TreeSet<>(set).descendingSet();
    }

    @GetMapping("/testLoggerLevel")
    public R<Object> testLoggerLevel() {

        System.out.println("log.isErrorEnabled(): " + log.isErrorEnabled());
        System.out.println("log.isWarnEnabled(): " + log.isWarnEnabled());
        System.out.println("log.isInfoEnabled(): " + log.isInfoEnabled());
        System.out.println("log.isDebugEnabled(): " + log.isDebugEnabled());
        System.out.println("log.isTraceEnabled(): " + log.isTraceEnabled());

        log.error("log.error");
        log.warn("log.warn");
        log.info("log.info");
        log.debug("log.debug");
        log.trace("log.trace");

        return R.boo(true);
    }
}
