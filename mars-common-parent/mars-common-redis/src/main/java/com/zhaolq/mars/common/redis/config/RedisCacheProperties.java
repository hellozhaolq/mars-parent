package com.zhaolq.mars.common.redis.config;

import lombok.Data;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

/**
 * Redis缓存有效期配置属性
 *
 * @author zhaolq
 * @since 2021/6/15 16:10
 */
@Data
@Configuration
public class RedisCacheProperties {

    private Duration defaultValidity = Duration.ofMinutes(10L);

    // private Map<String, Duration> validity = new HashMap<>();

}
