package com.zhaolq.mars.service.admin.controller.test;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.logging.LoggingSystem;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @Resource
    private DataSourceProperties dataSourceProperties;

    @Resource
    private LoggingSystem loggingSystem;

    @GetMapping("/getDataSourceProperties")
    @Operation(summary = "获取数据源配置", description = "获取数据源配置")
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
    @Parameter(name = "beanName", description = "Bean名称", style = ParameterStyle.SIMPLE)
    @Operation(summary = "获取Bean", description = "获取Bean")
    public R<Object> getBean(String beanName) {
        if (StringUtils.isEmpty(beanName)) {
            beanName = "userServiceImpl";
            /*
            IUserService bean = SpringContext.getInstance().getBean(beanName);
            UserEntity user = bean.getById(1);
            return R.success(user);
            */
        }
        Object bean = SpringContext.getInstance().getBean(beanName);
        if (ObjectUtils.isEmpty(bean)) {
            return R.failure("没有可用的名为 '" + beanName + "' 的bean");
        }
        return R.success("Simple name of the bean: " + bean.getClass().getSimpleName());
    }

}
