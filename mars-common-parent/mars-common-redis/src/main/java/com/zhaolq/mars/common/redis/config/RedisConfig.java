package com.zhaolq.mars.common.redis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.io.Serializable;

/**
 * Redis配置类
 *
 * 官方文档：
 *      https://docs.spring.io/spring-data/redis/docs/
 *      https://docs.spring.io/spring-data/redis/docs/2.3.9.RELEASE/reference/html/#reference
 *
 * @author zhaolq
 * @date 2021/6/15 21:14
 */
@Configuration
public class RedisConfig {

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration redisStandalone = new RedisStandaloneConfiguration();
        redisStandalone.setHostName("192.168.0.3");
        redisStandalone.setPort(6379);
        redisStandalone.setDatabase(0);
        redisStandalone.setPassword("123456789");

        // Redis 有很多种部署方式，单点（Standalone）、主从（Master-Slave）、哨兵（Sentinel）、集群（Cluster）。
        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(redisStandalone);
        lettuceConnectionFactory.setShareNativeConnection(false);

        return lettuceConnectionFactory;
    }

    @Primary
    @Bean
    public RedisTemplate<String, Serializable> redisTemplate(LettuceConnectionFactory lettuceConnectionFactory) {

        RedisTemplate<String, Serializable> redisTemplate = new RedisTemplate<>();

        // 字符串键序列化器
        StringRedisSerializer keySerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(keySerializer);
        redisTemplate.setHashKeySerializer(keySerializer);

        // 值序列化器
        GenericJackson2JsonRedisSerializer valueSerializer = new GenericJackson2JsonRedisSerializer();
        redisTemplate.setValueSerializer(valueSerializer);
        redisTemplate.setHashValueSerializer(valueSerializer);

        redisTemplate.setConnectionFactory(lettuceConnectionFactory);

        return redisTemplate;
    }



}
