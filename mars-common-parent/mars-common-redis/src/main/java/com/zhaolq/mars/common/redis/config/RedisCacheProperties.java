package com.zhaolq.mars.common.redis.config;

import java.time.Duration;

import org.springframework.context.annotation.Configuration;

import lombok.Data;

/**
 * Redis缓存有效期配置属性
 *
 * @Author zhaolq
 * @Date 2021/6/15 16:10
 */
@Data
@Configuration
public class RedisCacheProperties {

    private Duration defaultValidity = Duration.ofMinutes(10L);

    // private Map<String, Duration> validity = new HashMap<>();

}
