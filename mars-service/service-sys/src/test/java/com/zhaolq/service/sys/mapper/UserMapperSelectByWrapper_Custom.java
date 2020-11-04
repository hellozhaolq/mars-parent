package com.zhaolq.service.sys.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zhaolq.service.sys.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * 使用 Wrapper 自定义SQL
 *
 * @author zhaolq
 * @date 2020/10/21 22:01
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperSelectByWrapper_Custom {

    @Resource
    private UserMapper userMapper;

    /**
     * 使用 Wrapper 自定义SQL
     */
    @Test
    public void customSelectAll() {
        LambdaQueryWrapper<UserEntity> lambdaQueryWrapper = Wrappers.lambdaQuery();
        lambdaQueryWrapper.like(UserEntity::getName, "赵").lt(UserEntity::getAge, 30);
        // 使用lambda的或普通构造器都可以
        List<UserEntity> userList = userMapper.customSelectAllByWrapper(lambdaQueryWrapper);
        userList.forEach(System.out::println);
    }

}
