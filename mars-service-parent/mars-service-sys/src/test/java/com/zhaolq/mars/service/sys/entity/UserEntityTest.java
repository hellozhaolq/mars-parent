package com.zhaolq.mars.service.sys.entity;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhaolq.mars.api.sys.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.Date;

/**
 * AR模式：活动记录，一种领域模型模式。
 * 特点：一个模型类对应关系型数据库中的一个表，模型类的一个实例对应表中的一行记录。
 * 简言之，就是通过实体类对象直接进行表的CURD操作。
 *
 * @author zhaolq
 * @since 2021/5/19 14:01
 */
@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserEntityTest {

    @LocalServerPort
    private int port;

    private UserEntity userEntity;

    @BeforeEach
    public void before() {
        userEntity = new UserEntity();
        userEntity.setId("123456789");
        userEntity.setAccount("test");
        userEntity.setPassword("21218CCA77804D2BA1922C33E0151105");
        userEntity.setName("测试");
        userEntity.setNickName("昵称");
        userEntity.setSalt("YzcmCZNvbXocrsz9dm8e");
        userEntity.setSex(Byte.valueOf("1"));
        userEntity.setEmail("test@qq.com");
        userEntity.setMobile("13566667777");
        userEntity.setCountryCode("156");
        userEntity.setNationCode("01");
        userEntity.setPoliticalStatusCode("01");
        userEntity.setDeptId("1");
        userEntity.setCreateBy("JUnit");
        userEntity.setStatus(Byte.valueOf("1"));
        userEntity.setDelFlag(Byte.valueOf("0"));
    }

    /**
     * 启动contextLoads(应用程序上下文)，检测该单元测试是否可用
     */
    @Order(0)
    @Test
    @DisplayName("contextLoads()")
    public void contextLoads() {
        log.debug("测试使用的随机端口：" + port);
    }

    @Order(1)
    @Test
    @DisplayName("insertOrUpdate()")
    public void insertOrUpdate() {
        // 有源码得：不设置id就是insert语句；设置id，先根据id查询，存在记录执行update语句，不存在执行insert。
        boolean insert = userEntity.insertOrUpdate();
        log.debug(String.valueOf(insert));
    }

    @Order(2)
    @Test
    @DisplayName("select()")
    public void select() {
        UserEntity user = userEntity.selectById();
        // 查出来的是一个新对象，而非原对象，没有把值设置到原对象，所以下面输出false。
        log.debug(String.valueOf(user == userEntity));
        log.debug(user.toString());
    }

    @Order(3)
    @Test
    @DisplayName("selectOne()")
    public void selectOne() {
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>(userEntity);
        // 由源码得：实际调用selectList，如果size>1，则打印警告并返回第一条。
        // 而BaseMapper的selectOne()查询结果大于1条就会报错。
        UserEntity user = userEntity.selectOne(queryWrapper);
        log.debug(String.valueOf(user == userEntity));
        log.debug(user.toString());
    }

    @Order(4)
    @Test
    @DisplayName("update()")
    public void update() {
        // id出现在where中，其他出现在set中
        boolean update = userEntity.updateById();
        log.debug(String.valueOf(update));
    }

    @Order(5)
    @Test
    @DisplayName("delete()")
    public void delete() {
        // 由源码得：mp的处理，删除不存在的数据，逻辑上属于成功。所以影响条数为0也返回true。
        boolean delete = userEntity.deleteById();
        log.debug(String.valueOf(delete));
    }


}
