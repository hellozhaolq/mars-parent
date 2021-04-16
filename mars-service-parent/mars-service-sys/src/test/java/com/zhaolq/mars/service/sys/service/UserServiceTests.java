package com.zhaolq.mars.service.sys.service;

import com.zhaolq.mars.service.sys.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 *
 *
 * @author zhaolq
 * @date 2020/10/19 20:14
 */
@Slf4j
@SpringBootTest
public class UserServiceTests {

    @Resource
    private IUserService userService;

    @Test
    public void insert() {
        UserEntity userEntity = new UserEntity();

        userEntity.setAccount("test");
        userEntity.setPassword("21218CCA77804D2BA1922C33E0151105");
        userEntity.setName("test");
        userEntity.setNickName("测试");
        userEntity.setSalt("YzcmCZNvbXocrsz9dm8e");
        userEntity.setSex(Byte.valueOf("1"));
        userEntity.setEmail("test@qq.com");
        userEntity.setMobile("13889700023");
        userEntity.setCountryCode("156");
        userEntity.setNationCode("01");
        userEntity.setPoliticalStatusCode("01");
        userEntity.setDeptId(BigDecimal.valueOf(1L));
        userEntity.setCreateBy("JUnit");
        userEntity.setCreateTime(LocalDateTime.now());
        userEntity.setStatus(Byte.valueOf("1"));
        userEntity.setDelFlag(Byte.valueOf("0"));

        boolean boo = userService.save(userEntity);
        log.debug(String.valueOf(boo));
        System.out.println(boo);
    }


}
