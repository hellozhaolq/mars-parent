/*
 * Copyright (c) Zhaolq Technologies Co., Ltd. 大约40亿年前-9999. All rights reserved.
 */

package com.zhaolq.mars.common.swagger;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * swagger属性配置
 *
 * @Author zhaolq
 * @Date 2022/2/28 20:27
 * @Since 1.0.0
 */
@Component
@ConfigurationProperties(prefix = SwaggerProperties.PREFIX, ignoreInvalidFields = true)
@Data
public class SwaggerProperties {
    public static final String PREFIX = "swagger";

    /**
     * 是否开启swagger，生产环境一般关闭，所以这里定义一个变量
     */
    private Boolean enable;

    /**
     * 项目应用名
     */
    private String applicationName;

    /**
     * 项目版本信息
     */
    private String applicationVersion;

    /**
     * 项目描述信息
     */
    private String applicationDescription;

    /**
     * 接口调试地址
     */
    private String tryHost;

}
