package com.zhaolq.mars.service.sys.controller;

import cn.hutool.core.util.StrUtil;
import com.zhaolq.mars.common.log.LoggerLevelSettingService;
import com.zhaolq.mars.common.spring.utils.SpringContextUtils;
import com.zhaolq.mars.service.sys.entity.UserEntity;
import com.zhaolq.mars.service.sys.service.IUserService;
import com.zhaolq.mars.tool.core.result.R;
import com.zhaolq.mars.tool.core.utils.ObjectUtils;
import com.zhaolq.mars.tool.core.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.logging.LogLevel;
import org.springframework.boot.logging.LoggerConfiguration;
import org.springframework.boot.logging.LoggingSystem;
import org.springframework.boot.logging.LoggingSystemProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.*;

import static org.springframework.boot.logging.LoggingSystem.ROOT_LOGGER_NAME;

/**
 * 测试
 *
 * @author zhaolq
 * @date 2021/6/17 17:47
 */
@Slf4j
@RestController
@RequestMapping("/appTest")
public class AppTestController {

    @Resource
    private DataSourceProperties dataSourceProperties;

    @Resource
    private LoggerLevelSettingService loggerLevelSettingService;

    @Resource
    private LoggingSystem loggingSystem;

    @GetMapping("/getDataSourceProperties")
    public R<Object> getDataSourceProperties() {
        Map<String, String> map = new TreeMap<>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                // 可使treeMap保存元素加入的顺序
                return 1;
            }
        });
        map.put("DriverClassName", dataSourceProperties.getDriverClassName());
        map.put("Url", dataSourceProperties.getUrl());
        map.put("Username", dataSourceProperties.getUsername());
        map.put("Password", dataSourceProperties.getPassword());
        return R.success(map);
    }

    @GetMapping("/getBean")
    public R<Object> getBean(String beanName) {
        if (StringUtils.isEmpty(beanName)) {
            beanName = "userServiceImpl";
            /*
            IUserService bean = SpringContextUtils.getInstance().getBean(beanName);
            UserEntity user = bean.getById(1);
            return R.success(user);
            */
        }
        Object bean = SpringContextUtils.getInstance().getBean(beanName);
        if (ObjectUtils.isEmpty(bean)) {
            return R.success("No bean named '" + beanName + "' available ");
        }
        return R.success("bean的简单名称: " + bean.getClass().getSimpleName());
    }

    @GetMapping("/setLoggerLevel")
    public R<Object> setLoggerLevel(String rootLevel, String marsLevel) {

        // loggerLevelSettingService.setRootLoggerLevel(level);
        loggingSystem.setLogLevel(ROOT_LOGGER_NAME, LogLevel.valueOf(rootLevel));

        // loggerLevelSettingService.setLoggerLevel(level);
        loggingSystem.setLogLevel("com.zhaolq", LogLevel.valueOf(marsLevel));

        // loggerLevelSettingService.setDegradationLoggerLevel(level);


        System.out.println("log.isErrorEnabled(): " + log.isErrorEnabled());
        System.out.println("log.isWarnEnabled(): " + log.isWarnEnabled());
        System.out.println("log.isInfoEnabled(): " + log.isInfoEnabled());
        System.out.println("log.isDebugEnabled(): " + log.isDebugEnabled());
        System.out.println("log.isTraceEnabled(): " + log.isTraceEnabled());

        log.error("log.trace");
        log.warn("log.warn");
        log.info("log.info");
        log.debug("log.debug");
        log.trace("log.trace");

        return R.boo(true);
    }

}
