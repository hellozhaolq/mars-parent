/*
 * Copyright (c) Zhaolq Technologies Co., Ltd. 大约40亿年前-9999. All rights reserved.
 */

package com.zhaolq.mars.common.core.enums;

import java.util.Arrays;
import java.util.List;

/**
 * 各类开发生产环境枚举
 *
 * @Author zhaolq
 * @Date 2022/8/22 19:27
 */
public enum Profile {

    /**
     * 开发
     */
    DEV("dev"),
    /**
     * 系统集成测试
     */
    SIT("sit"),
    /**
     * 用户验收测试
     */
    UAT("uat"),
    /**
     * 性能评估测试（压测）
     */
    PET("pet"),
    /**
     * 仿真
     */
    SIM("sim"),
    /**
     * 产品/正式/生产
     */
    PROD("prod");

    // ---------------------------------------------------------------

    private final String value;

    private Profile(String value) {
        this.value = value;
    }

    /**
     * 允许的配置文件列表。Arrays.asList返回的集合无法进行add和remove操作
     *
     * @return java.util.List<java.lang.String>
     */
    public static List<String> allowedProfiles() {
        return Arrays.asList(
                DEV.getValue(),
                SIT.getValue(),
                UAT.getValue(),
                PET.getValue(),
                SIM.getValue(),
                PROD.getValue());
    }

    public String getValue() {
        return this.value;
    }
}
