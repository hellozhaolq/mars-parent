package com.zhaolq.mars.service.sys.controller;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhaolq.mars.common.valid.group.Add;
import com.zhaolq.mars.common.valid.group.Edit;
import com.zhaolq.mars.service.sys.entity.RoleEntity;
import com.zhaolq.mars.service.sys.entity.UserEntity;
import com.zhaolq.mars.service.sys.service.IUserService;
import com.zhaolq.mars.tool.core.result.R;
import com.zhaolq.mars.tool.core.result.ResultCode;
import com.zhaolq.mars.tool.core.io.FileUtils;
import com.zhaolq.mars.tool.core.utils.StringUtils;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.util.Collections;
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

    private IUserService userService;

    /**
     * 单个新增
     *
     * @param userEntity
     * @return com.zhaolq.mars.service.sys.entity.UserEntity
     * @throws
     */
    @PostMapping
    @ApiOperation(value = "单个新增", notes = "单个新增")
    public R<Boolean> post(@Validated({Add.class}) @RequestBody(required = true) UserEntity userEntity) {
        // 检查用户是否存在
        QueryWrapper<UserEntity> wrapper = new QueryWrapper<>(new UserEntity().setAccount(userEntity.getAccount()));
        UserEntity result = userService.getOne(wrapper);
        if (ObjectUtil.isNotNull(result)) {
            return R.failure(ResultCode.USER_HAS_EXISTED);
        }
        // 不存在，则新增
        boolean boo = userService.save(userEntity);
        return R.boo(boo);
    }

    /**
     * 单个删除
     *
     * @param id
     * @return com.zhaolq.mars.service.sys.entity.UserEntity
     * @throws
     */
    @DeleteMapping("/{id}")
    @ApiOperation(value = "单个删除", notes = "单个删除")
    public R<Boolean> delete(@PathVariable("id") @NotNull(message = "缺少id") String id) {
        UserEntity userEntity = userService.getById(id);
        if (userEntity == null) {
            return R.failure(ResultCode.USER_NOT_EXISTED);
        }
        boolean boo = userService.removeById(id);
        return R.boo(boo);
    }

    /**
     * 单个修改
     *
     * @param userEntity
     * @return com.zhaolq.mars.service.sys.entity.UserEntity
     * @throws
     */
    @PutMapping
    @ApiOperation(value = "单个修改", notes = "单个修改")
    public R<Boolean> put(@Validated({Edit.class}) @RequestBody UserEntity userEntity) {
        UserEntity userEntityTemp = userService.getById(userEntity.getId());
        if (userEntityTemp == null) {
            return R.failure(ResultCode.USER_NOT_EXISTED);
        }
        boolean boo = userService.updateById(userEntity);
        return R.boo(boo);
    }

    /**
     * 单个查询
     *
     * @param userEntity
     * @return com.zhaolq.mars.service.sys.entity.UserEntity
     * @throws
     */
    @GetMapping
    @ApiOperation(value = "单个查询", notes = "单个查询")
    public R<UserEntity> get(UserEntity userEntity) {
        Assert.notNull(userEntity, ResultCode.PARAM_NOT_COMPLETE.getDescCh());
        boolean condition = userEntity == null || (StringUtils.isEmpty(userEntity.getId()) && StringUtils.isEmpty(userEntity.getAccount()));
        if (condition) {
            return R.failure(ResultCode.PARAM_NOT_COMPLETE);
        }
        QueryWrapper<UserEntity> wrapper = new QueryWrapper<>(userEntity);
        userEntity = userService.getOne(wrapper);
        if (userEntity == null) {
            return R.failure(ResultCode.USER_NOT_EXISTED);
        }
        return R.success(userEntity);
    }

    @GetMapping("/list")
    @ApiOperation(value = "列表查询", notes = "列表查询")
    public R<List<UserEntity>> getList(UserEntity userEntity) {
        QueryWrapper<UserEntity> wrapper = new QueryWrapper<>(userEntity);
        return R.success(userService.list(wrapper));
    }

    /**
     * 单个查询，携带角色列表
     *
     * @param userEntity
     * @return com.zhaolq.mars.tool.core.result.R<com.zhaolq.mars.service.sys.entity.UserEntity>
     */
    @GetMapping("/withRole")
    public R<UserEntity> getWithRole(UserEntity userEntity) {
        Assert.notNull(userEntity, ResultCode.PARAM_NOT_COMPLETE.getDescCh());
        boolean condition = userEntity == null || (StringUtils.isEmpty(userEntity.getId()) && StringUtils.isEmpty(userEntity.getAccount()));
        if (condition) {
            return R.failure(ResultCode.PARAM_NOT_COMPLETE);
        }
        userEntity = userService.getWithRole(userEntity, null);
        if (userEntity == null) {
            return R.failure(ResultCode.USER_NOT_EXISTED);
        }
        // 角色根据ID排序
        Collections.sort(userEntity.getRoleList(), (r1, r2) -> Integer.valueOf(r1.getId()).compareTo(Integer.valueOf(r2.getId())));
        return R.success(userEntity);
    }

    /**
     * 列表查询，携带角色列表
     *
     * @param userEntity
     * @return com.zhaolq.mars.tool.core.result.R<java.util.List < com.zhaolq.mars.service.sys.entity.UserEntity>>
     */
    @GetMapping("/withRoleList")
    public R<List<UserEntity>> getWithRoleList(UserEntity userEntity) {
        List<UserEntity> list = userService.getWithRoleList(userEntity, null);
        if (list != null) {
            for (UserEntity u : list) {
                // 角色根据ID排序(roleId转integer排序)
                Collections.sort(u.getRoleList(), (r1, r2) -> Integer.valueOf(r1.getId()).compareTo(Integer.valueOf(r2.getId())));
            }
        }
        return R.success(list);
    }

    /**
     * 分页查询
     * 问题：暂不支持排序
     *
     * @param page
     * @param userEntity
     * @return com.zhaolq.mars.tool.core.result.R<com.baomidou.mybatisplus.core.metadata.IPage<com.zhaolq.mars.service.sys.entity.UserEntity>>
     */
    @GetMapping("/page")
    public R<IPage<UserEntity>> getPage(Page<UserEntity> page, UserEntity userEntity) {
        QueryWrapper<UserEntity> wrapper = new QueryWrapper<>(userEntity);
        page = userService.page(page, wrapper);
        return R.success(page);
    }

    /**
     * 分页查询。post请求接受两个参数，只能有一个参数从RequestBody中获取。
     * 问题：暂不支持排序
     *
     * @param page
     * @param userEntity
     * @return com.zhaolq.mars.tool.core.result.R<com.baomidou.mybatisplus.core.metadata.IPage<com.zhaolq.mars.service.sys.entity.UserEntity>>
     */
    @PostMapping("/page")
    public R<IPage<UserEntity>> getPage2(Page<UserEntity> page, @RequestBody(required = false) UserEntity userEntity) {
        QueryWrapper<UserEntity> wrapper = new QueryWrapper<>(userEntity);
        page = userService.page(page, wrapper);
        return R.success(page);
    }

    /**
     * 分页查询，携带角色列表
     * 问题：1、暂不支持排序；2、一对多分页的total值等于多的一方
     *
     * @param
     * @return com.zhaolq.mars.tool.core.result.R<com.baomidou.mybatisplus.core.metadata.IPage<com.zhaolq.mars.service.sys.entity.UserEntity>>
     */
    @GetMapping("/withRolePage")
    public R<IPage<UserEntity>> getWithRolePage(Page<UserEntity> page, UserEntity userEntity) {
        return R.success(userService.getWithRolePage(page, userEntity, null));
    }

    @GetMapping("exportExcel")
    public void exportExcel(Page<UserEntity> page, HttpServletResponse response) {
        File file = userService.createExcelFile(page);
        FileUtils.downloadFile(response, file, file.getName());
    }

    @PostMapping("importExcel")
    public R<Boolean> importExcel() {
        return R.boo(true);
    }


}

