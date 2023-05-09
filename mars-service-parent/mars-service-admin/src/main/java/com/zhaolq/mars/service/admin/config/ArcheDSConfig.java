package com.zhaolq.mars.service.admin.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

/**
 * Arche是希腊文，英文是principle，字面意义是开始、太初、起源。
 * 当一事物（存有物、或事件、或认识）以某些方式从“另一事物“出发，后者即称为开始或起源，在中国被翻译为始元。
 *
 * @author zhaolq
 * @date 2023/4/25 23:35
 * @since 1.0.0
 */
@Configuration
@MapperScan(basePackages = "com.zhaolq.service.base.mapper.arche.*", sqlSessionTemplateRef = "archeSqlSessionTemplate")
@Slf4j
public class ArcheDSConfig {
}
