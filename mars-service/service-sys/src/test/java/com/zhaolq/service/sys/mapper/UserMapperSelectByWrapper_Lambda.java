package com.zhaolq.service.sys.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.zhaolq.service.sys.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 *
 * @author zhaolq
 * @date 2020/10/21 22:00
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperSelectByWrapper_Lambda {

    @Resource
    private UserMapper userMapper;

    /**
     * 4种Lambda条件构造器。
     * Lambda条件构造器的好处：防止列名误写
     */
    @Test
    public void selectByWrapper_Lambda() {
        // LambdaQueryWrapper<UserEntity> lambdaQueryWrapper1 = new QueryWrapper<UserEntity>().lambda();
        // LambdaQueryWrapper<UserEntity> lambdaQueryWrapper2 = new LambdaQueryWrapper<>();
        LambdaQueryWrapper<UserEntity> lambdaQueryWrapper3 = Wrappers.lambdaQuery();
        lambdaQueryWrapper3.like(UserEntity::getName, "赵").lt(UserEntity::getAge, 30);
        List<UserEntity> userList = userMapper.selectList(lambdaQueryWrapper3);
        userList.forEach(System.out::println);

        // 第4种比较特殊
        List<UserEntity> userList4 = new LambdaQueryChainWrapper<UserEntity>(userMapper)
                .like(UserEntity::getName, "赵").ge(UserEntity::getAge, 30).list();
        userList4.forEach(System.out::println);
    }

}
