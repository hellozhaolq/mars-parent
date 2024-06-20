package com.zhaolq.mars.service.admin.config;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Redis配置类
 * <p>
 * 官方文档：
 * https://docs.spring.io/spring-data/redis/docs/
 * https://docs.spring.io/spring-data/redis/docs/2.3.9.RELEASE/reference/html/#reference
 *
 * @Author zhaolq
 * @Date 2021/6/15 21:14
 */
@Configuration
public class RedisConfig {

    @Value("${spring.data.redis.host}")
    private String host;

    @Value("${spring.data.redis.port:6379}")
    private int port;

    @Value("${spring.data.redis.database:0}")
    private int database;

    @Value("${spring.data.redis.password}")
    private String password;

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration redisStandalone = new RedisStandaloneConfiguration();
        redisStandalone.setHostName(host);
        redisStandalone.setPort(port);
        redisStandalone.setDatabase(database);
        redisStandalone.setPassword(password);

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
