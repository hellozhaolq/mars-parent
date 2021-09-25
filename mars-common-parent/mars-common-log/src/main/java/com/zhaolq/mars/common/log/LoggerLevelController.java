package com.zhaolq.mars.common.log;

import com.zhaolq.mars.tool.core.lang.Assert;
import com.zhaolq.mars.tool.core.result.R;
import com.zhaolq.mars.tool.core.result.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.logging.LogLevel;
import org.springframework.boot.logging.LoggerConfiguration;
import org.springframework.boot.logging.LoggingSystem;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.*;

/**
 * 日志级别设置控制器
 * 需要登录拦截
 *
 * @author zhaolq
 * @since 2021/6/21 20:13
 */
@Slf4j
@RestController
@RequestMapping("/logger")
public class LoggerLevelController {

    /**
     * LoggingSystem服务是SpringBoot对日志系统的抽象，是一个顶层的抽象类。他有很多具体的实现。
     * 有了LoggingSystem以后，我们就可以通过他来动态的修改日志级别。他帮我们屏蔽掉了底层的具体日志框架。
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
        Assert.notBlank(loggerLevel.getName(), "Name must not be null");
        // 由Log4J2LoggingSystem.convertLoggerConfig方法可知跟记录器(ROOT)名称可能默认为null或""，这也是loggingSystem.getLoggerConfiguration("ROOT")获取不到记录器的原因
        // LoggerConfiguration configuration = this.loggingSystem.getLoggerConfiguration(loggerLevel.getName());
        Collection<LoggerConfiguration> configurations = this.loggingSystem.getLoggerConfigurations();
        if (configurations == null) {
            return null;
        }
        for (LoggerConfiguration configuration : configurations) {
            if (configuration.getName().equalsIgnoreCase(loggerLevel.getName())) {
                loggerLevel = new LoggerLevel(configuration);
            }
        }
        return loggerLevel;
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
        Assert.notBlank(loggerLevel.getName(), "Name must not be empty");
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
