package com.zhaolq.service.sys.controller;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhaolq.common.valid.group.Add;
import com.zhaolq.core.result.R;
import com.zhaolq.service.sys.entity.UserEntity;
import com.zhaolq.service.sys.service.IUserService;
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
     * @return com.zhaolq.service.sys.entity.UserEntity
     * @throws
     */
    @PostMapping
    public R<Boolean> post(@Validated({Add.class}) @RequestBody(required = false) UserEntity userEntity) {
        Assert.notNull(userEntity, "条件不足！");
        userEntity.setStatus(1);
        userEntity.setCreateBy("admin");
        userEntity.setDelFlag(0);
        boolean boo = userService.save(userEntity);
        return R.boo(boo);
    }

    /**
     * 单个删除
     *
     * @param id
     * @return com.zhaolq.service.sys.entity.UserEntity
     * @throws
     */
    @DeleteMapping("/{id}")
    public R<Boolean> delete(@PathVariable("id") @NotNull(message = "缺少id") Long id) {
        Assert.notNull(id, "条件不足！");
        boolean boo = userService.removeById(id);
        return R.boo(boo);
    }

    /**
     * 单个修改
     *
     * @param userEntity
     * @return com.zhaolq.service.sys.entity.UserEntity
     * @throws
     */
    @PutMapping
    public R<Boolean> put(@RequestBody UserEntity userEntity) {
        Assert.notNull(userEntity, "条件不足！");
        boolean boo = userService.updateById(userEntity);
        return R.boo(boo);
    }

    /**
     * 单个查询
     *
     * @param userEntity
     * @return com.zhaolq.service.sys.entity.UserEntity
     * @throws
     */
    @GetMapping
    public R<UserEntity> get(UserEntity userEntity) {
        Assert.notNull(userEntity, "条件不足！");
        QueryWrapper<UserEntity> wrapper = new QueryWrapper<>(userEntity);
        userEntity = userService.getOne(wrapper);
        return R.success(userEntity);
    }

    @GetMapping("/detail")
    public R<UserEntity> getDetail(UserEntity userEntity) {
        return null;
    }

    @GetMapping("/list")
    public R<List<UserEntity>> getList(UserEntity userEntity) {
        return null;
    }

    @GetMapping("/listDetail")
    public R<List<UserEntity>> getListDetail(UserEntity userEntity) {
        return null;
    }

}

