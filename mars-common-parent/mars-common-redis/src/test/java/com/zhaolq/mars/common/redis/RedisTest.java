package com.zhaolq.mars.common.redis;

import java.io.Serializable;
import java.util.Properties;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import lombok.extern.slf4j.Slf4j;

/**
 *
 *
 * @Author zhaolq
 * @Date 2021/6/15 20:29
 */
@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@SpringBootApplication
public class RedisTest {

    private User user;

    @Autowired
    private RedisRepo<User> userRedisRepo;
    @Autowired
    private RedisRepo<String> strRedisRepo;

    @Autowired
    private RedisTemplate<String, String> strRedisTemplate;
    @Autowired
    private RedisTemplate<String, Serializable> serializableRedisTemplate;

    @BeforeEach
    public void before() {
        user = new User()
                .setId("123456789")
                .setAccount("test")
                .setPassword("21218CCA77804D2BA1922C33E0151105")
                .setName("测试");
    }

    /**
     * 启动contextLoads(应用程序上下文)，检测该单元测试是否可用
     */
    @Order(0)
    @DisplayName("contextLoads()")
    @Test
    public void contextLoads() {
        Properties properties = System.getProperties();
        properties.forEach((key, value) -> System.out.println(key + "=" + value));
    }

    /**
     * <String, String>
     */
    @Order(1)
    @DisplayName("testString()")
    @Test
    public void testString() {
        strRedisTemplate.opsForValue().set("strKey", "zwqh");
        System.out.println(strRedisTemplate.opsForValue().get("strKey"));
    }

    /**
     * <String, Serializable>
     */
    @Order(2)
    @DisplayName("testSerializable()")
    @Test
    public void testSerializable() {
        serializableRedisTemplate.opsForValue().set("user", user);
        User user2 = (User) serializableRedisTemplate.opsForValue().get("user");
        System.out.println("user:" + user2.getId() + "," +
                user2.getAccount() + "," +
                user2.getPassword() + "," +
                user2.getName());
    }

    @Order(3)
    @DisplayName("redisRepo()")
    @Test
    public void redisRepo() {
        strRedisRepo.set("key1", "key1");
        userRedisRepo.set("key2", user);
    }

}
