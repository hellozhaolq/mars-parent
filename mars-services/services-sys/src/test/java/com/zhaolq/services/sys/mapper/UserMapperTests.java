package com.zhaolq.services.sys.mapper;

import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 *
 *
 * @author zhaolq
 * @date 2020/10/20 21:12
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTests {

    @Resource
    private UserMapper userMapper;

}
