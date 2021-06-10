package com.zhaolq.mars.service.sys.controller;

import com.zhaolq.mars.tool.core.result.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 测试控制器
 *
 * @author zhaolq
 * @date 2021/6/10 11:22
 */
@Slf4j
@RestController
public class TestController {

    @Resource
    private DataSourceProperties dataSourceProperties;

    @GetMapping("/test")
    public R<String> test() {
        System.out.println(dataSourceProperties.getDriverClassName());
        System.out.println(dataSourceProperties.getUrl());
        System.out.println(dataSourceProperties.getUsername());
        System.out.println(dataSourceProperties.getPassword());
        return R.success("");
    }

}
