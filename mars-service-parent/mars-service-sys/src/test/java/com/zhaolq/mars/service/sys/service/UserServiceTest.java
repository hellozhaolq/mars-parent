package com.zhaolq.mars.service.sys.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zhaolq.mars.api.sys.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 *
 *
 * @author zhaolq
 * @date 2021/5/19 17:48
 */
@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserServiceTest {

    @LocalServerPort
    private int port;

    @Resource
    private IUserService userService;

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
    @DisplayName("getOne()")
    public void getOne() {
        UserEntity one = userService.getOne(Wrappers.<UserEntity>lambdaQuery().gt(UserEntity::getAge, 60));
        System.out.println(one);
    }

    /**
     * 为什么MP把批量操作写在了service层？官方解释如下：
     *   sql长度有限制，海量数据单条sql无法执行，就算可执行也容易引起内存溢出(Out Of Memory，简称OOM)、jdbc连接超时等，
     *   不同数据库批量语法不一样，易于通用，目前的解决方法是循环预处理，批量提交，虽然性能比单条sql慢，但是可以解决问题。
     *
     * 是否需要注意事务问题呢？
     *
     */

    @Order(2)
    @Test
    @DisplayName("saveBatch()")
    public void saveBatch() {
        UserEntity userEntity1 = new UserEntity();
        userEntity1 = new UserEntity();
        userEntity1.setId("111111111");
        userEntity1.setAccount("test1");

        UserEntity userEntity2 = new UserEntity();
        userEntity2 = new UserEntity();
        userEntity2.setId("222222222");
        userEntity2.setAccount("test2");

        List<UserEntity> userList = Arrays.asList(userEntity1, userEntity2);
        boolean saveBatch = userService.saveBatch(userList);
        System.out.println(saveBatch);
    }

    @Order(3)
    @Test
    @DisplayName("saveOrUpdateBatch()")
    public void saveOrUpdateBatch() {
        UserEntity userEntity3 = new UserEntity();
        userEntity3 = new UserEntity();
        userEntity3.setId("333333333");
        userEntity3.setAccount("test3");

        UserEntity userEntity2 = new UserEntity();
        userEntity2 = new UserEntity();
        userEntity2.setId("222222222");
        userEntity2.setAccount("test2");

        List<UserEntity> userList = Arrays.asList(userEntity3, userEntity2);
        boolean saveOrUpdateBatch = userService.saveOrUpdateBatch(userList);
        System.out.println(saveOrUpdateBatch);
    }

    @Order(4)
    @Test
    @DisplayName("chainLambdaQuery()")
    public void chainLambdaQuery() {
        List<UserEntity> userList = userService.lambdaQuery().gt(UserEntity::getAge, 60).like(UserEntity::getName, "张").list();
        userList.forEach(System.out::println);
    }

    @Order(5)
    @Test
    @DisplayName("chainLambdaUpdate()")
    public void chainLambdaUpdate() {
        boolean update = userService.lambdaUpdate().like(UserEntity::getAccount, "test").set(UserEntity::getAge, 10).update();
        System.out.println(update);
    }

    @Order(6)
    @Test
    @DisplayName("remove()")
    public void remove() {
        // boolean removeByIds = userService.removeByIds(Arrays.asList("111111111", "222222222", "333333333"));
        // System.out.println(removeByIds);
        boolean remove = userService.lambdaUpdate().like(UserEntity::getAccount, "test").remove();
        System.out.println(remove);
    }

}
