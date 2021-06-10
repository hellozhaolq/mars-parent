package com.zhaolq.mars.service.sys.controller;

import com.zhaolq.mars.common.property.datasource.DataSourceInfo;
import com.zhaolq.mars.common.property.server.ServerInfo;
import com.zhaolq.mars.tool.core.result.R;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
@RequestMapping("/test")
public class TestController {

    @Resource
    private ServerInfo serverInfo;
    @Resource
    private DataSourceInfo dataSourceInfo;

    @GetMapping("/one")
    @ApiOperation(value = "单个查询", notes = "单个查询")
    public R<DataSourceInfo> get() {
        return R.success(dataSourceInfo);
    }

}
