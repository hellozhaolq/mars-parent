package com.zhaolq.service.sys.controller;


import cn.hutool.core.lang.Assert;
import com.zhaolq.common.valid.group.Add;
import com.zhaolq.core.result.R;
import com.zhaolq.service.sys.entity.UserEntity;
import com.zhaolq.service.sys.service.IUserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.nio.charset.Charset;
import java.util.List;

/**
 * <p>
 * 用户管理 前端控制器
 * </p>
 *
 * @author zhaolq
 * @since 2020-10-29
 */
@Validated
@Slf4j
@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    IUserService userService;

    /**
     * 单个新增
     *
     * @param userEntity
     * @return com.zhaolq.service.sys.entity.UserEntity
     * @throws
     */
    @PostMapping("/user")
    public Boolean postUser(@Validated({Add.class}) @RequestBody(required = false) UserEntity userEntity) {
        Assert.notNull(userEntity, "条件不足！");
        userEntity.setStatus(1);
        userEntity.setCreateBy("admin");
        userEntity.setDelFlag(0);
        boolean boo = userService.save(userEntity);
        return boo;
    }

    /**
     * 单个删除
     *
     * @param id
     * @return com.zhaolq.service.sys.entity.UserEntity
     * @throws
     */
    @DeleteMapping("/user")
    public Boolean deleteUser(@NotNull(message = "缺少id") Long id) {
        Assert.notNull(id, "条件不足！");
        boolean boo = userService.removeById(id);
        return boo;
    }

    /**
     * 单个修改
     *
     * @param userEntity
     * @return com.zhaolq.service.sys.entity.UserEntity
     * @throws
     */
    @PutMapping("/user")
    public Boolean putUser(@RequestBody UserEntity userEntity) {
        Assert.notNull(userEntity, "条件不足！");
        boolean boo = userService.updateById(userEntity);
        return boo;
    }

    /**
     * 单个查询
     *
     * @param userEntity
     * @return com.zhaolq.service.sys.entity.UserEntity
     * @throws
     */
    @GetMapping("/user")
    public R<UserEntity> getUser(UserEntity userEntity) {
        Assert.notNull(userEntity, "条件不足！");
        System.out.println(userEntity);

        System.out.println("当前JRE版本：" + System.getProperty("java.version"));
        System.out.println("当前JVM的默认字符集：" + Charset.defaultCharset());
        // 查看JVM运行时所使用的编码。Windows默认file.encoding=”GBK”；Linux默认file.encoding=”UTF-8”。
        System.out.println(System.getProperty("file.encoding"));
        System.out.println(System.getProperty("file.encoding"));
        System.out.println(System.getProperty("file.encoding"));
        System.out.println("啊阿斯顿来看看啊slop参加但是领导阿斯蒂芬asdfasdfa");
        System.out.println("啊阿斯顿来看看啊slop参加但是领导阿斯蒂芬asdfasdfa");
        System.out.println("啊阿斯顿来看看啊slop参加但是领导阿斯蒂芬asdfasdfa");
        log.info("啊阿斯顿来看看啊slop参加但是领导阿斯蒂芬asdfasdfa");
        log.info("啊阿斯顿来看看啊slop参加但是领导阿斯蒂芬asdfasdfa");
        log.info("啊阿斯顿来看看啊slop参加但是领导阿斯蒂芬asdfasdfa");

        // QueryWrapper<UserEntity> wrapper = new QueryWrapper<>(userEntity);
        // userEntity = userService.getOne(wrapper);
        return R.success(null);
    }

    @GetMapping("/userList")
    public List<UserEntity> getUserList(UserEntity userEntity) {
        return null;
    }

    @GetMapping("/userDetail")
    public UserEntity getUserDetail(UserEntity userEntity) {
        return null;
    }

    @GetMapping("/userDetailList")
    public List<UserEntity> getUserDetailList(UserEntity userEntity) {
        return null;
    }

}

