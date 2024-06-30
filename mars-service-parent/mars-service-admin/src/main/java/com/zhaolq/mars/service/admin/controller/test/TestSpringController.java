package com.zhaolq.mars.service.admin.controller.test;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.boot.logging.LoggingSystem;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zaxxer.hikari.HikariDataSource;
import com.zhaolq.mars.common.core.exception.BaseRuntimeException;
import com.zhaolq.mars.common.core.result.ErrorEnum;
import com.zhaolq.mars.common.core.result.R;
import com.zhaolq.mars.common.spring.utils.SpringContext;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterStyle;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

/**
 * 测试
 *
 * @Author zhaolq
 * @Date 2021/6/17 17:47
 */
@Slf4j
@RestController
@Tag(name = "测试模块", description = "测试模块")
@RequestMapping(path = "/testSpring", consumes = {MediaType.ALL_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
public class TestSpringController {

    @Resource(name = "baseDataSource")
    private HikariDataSource baseDataSource;

    @Resource(name = "archeDataSource")
    private HikariDataSource archeDataSource;

    @Resource
    private SpringContext springContext;

    @Resource
    private LoggingSystem loggingSystem;

    @GetMapping("/getBaseDataSource")
    @Operation(summary = "获取数据源", description = "获取数据源")
    public R<Object> getBaseDataSource() {
        Map<String, String> map = new TreeMap<>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                // 可使treeMap保存元素加入的顺序
                return 1;
            }
        });
        map.put("DriverClassName", baseDataSource.getDriverClassName());
        map.put("Url", baseDataSource.getJdbcUrl());
        map.put("Username", baseDataSource.getUsername());
        map.put("Password", baseDataSource.getPassword());
        map.put("MaximumPoolSize", String.valueOf(baseDataSource.getMaximumPoolSize()));
        map.put("PoolName", String.valueOf(baseDataSource.getPoolName()));
        AtomicInteger a = new AtomicInteger();
        return R.success(map);
    }

    @GetMapping("/getArcheDataSource")
    @Operation(summary = "获取数据源", description = "获取数据源")
    public R<Object> getArcheDataSource() {
        Map<String, String> map = new TreeMap<>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                // 可使treeMap保存元素加入的顺序
                return 1;
            }
        });
        map.put("DriverClassName", archeDataSource.getDriverClassName());
        map.put("Url", archeDataSource.getJdbcUrl());
        map.put("Username", archeDataSource.getUsername());
        map.put("Password", archeDataSource.getPassword());
        map.put("MaximumPoolSize", String.valueOf(archeDataSource.getMaximumPoolSize()));
        map.put("PoolName", String.valueOf(archeDataSource.getPoolName()));
        return R.success(map);
    }

    @GetMapping("/getBean")
    @Parameter(name = "beanName", description = "Bean名称", style = ParameterStyle.SIMPLE)
    @Operation(summary = "获取Bean", description = "获取Bean")
    public R<Object> getBean(@RequestParam("beanName") String beanName) {
        if (StringUtils.isEmpty(beanName)) {
            throw new BaseRuntimeException(ErrorEnum.PARAM_NOT_COMPLETE);
        }
        Object bean = null;
        try {
            bean = springContext.getBean(beanName);
        } catch (BeansException e) {
            return R.success(e.getMessage());
        }
        return R.success("The simple name of the bean: " + bean.getClass().getSimpleName());
    }

}
