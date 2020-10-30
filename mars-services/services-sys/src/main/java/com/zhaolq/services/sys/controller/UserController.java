package com.zhaolq.services.sys.controller;


import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhaolq.common.valid.group.Add;
import com.zhaolq.services.sys.entity.UserEntity;
import com.zhaolq.services.sys.service.IUserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
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
     * @return com.zhaolq.services.sys.entity.UserEntity
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
     * @return com.zhaolq.services.sys.entity.UserEntity
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
     * @return com.zhaolq.services.sys.entity.UserEntity
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
     * @return com.zhaolq.services.sys.entity.UserEntity
     * @throws
     */
    @GetMapping("/user")
    public UserEntity getUser(UserEntity userEntity) {
        Assert.notNull(userEntity, "条件不足！");
        QueryWrapper<UserEntity> wrapper = new QueryWrapper<>(userEntity);
        userEntity = userService.getOne(wrapper);
        return userEntity;
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

