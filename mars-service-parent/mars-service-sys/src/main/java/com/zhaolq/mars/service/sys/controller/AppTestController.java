package com.zhaolq.mars.service.sys.controller;

import com.zhaolq.mars.common.spring.utils.SpringContextUtils;
import com.zhaolq.mars.service.sys.service.IUserService;
import com.zhaolq.mars.tool.core.result.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 测试
 *
 * @author zhaolq
 * @date 2021/6/17 17:47
 */
@Slf4j
@RestController
public class AppTestController {

    @Resource
    private DataSourceProperties dataSourceProperties;

    @GetMapping("/getProperties")
    public R<String> getProperties() {
        System.out.println(dataSourceProperties.getDriverClassName());
        System.out.println(dataSourceProperties.getUrl());
        System.out.println(dataSourceProperties.getUsername());
        System.out.println(dataSourceProperties.getPassword());
        return R.success("");
    }

    @GetMapping("/getBean")
    public String getBean(String beanName) {
        Object bean = SpringContextUtils.getInstance().getBean(beanName);
        return bean.getClass().getSimpleName();
    }

}
