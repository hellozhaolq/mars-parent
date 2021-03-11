package com.zhaolq.mars.service.sys.controller;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhaolq.core.utils.FileUtils;
import com.zhaolq.mars.common.valid.group.Add;
import com.zhaolq.mars.service.sys.entity.UserEntity;
import com.zhaolq.mars.service.sys.service.IUserService;
import com.zhaolq.mars.tool.core.result.R;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.File;
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
     * @return com.zhaolq.mars.service.sys.entity.UserEntity
     * @throws
     */
    @PostMapping
    @ApiOperation(value = "添加", notes = "添加")
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
     * @return com.zhaolq.mars.service.sys.entity.UserEntity
     * @throws
     */
    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除", notes = "删除")
    public R<Boolean> delete(@PathVariable("id") @NotNull(message = "缺少id") Long id) {
        Assert.notNull(id, "条件不足！");
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
    @ApiOperation(value = "编辑修改", notes = "编辑修改")
    public R<Boolean> put(@RequestBody UserEntity userEntity) {
        Assert.notNull(userEntity, "条件不足！");
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
    @ApiOperation(value = "查询", notes = "查询")
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
    @ApiOperation(value = "列表查询", notes = "列表查询")
    public R<List<UserEntity>> getList(UserEntity userEntity) {
        QueryWrapper<UserEntity> wrapper = new QueryWrapper<>(userEntity);
        return R.success(userService.list(wrapper));
    }

    @GetMapping("/listDetail")
    public R<List<UserEntity>> getListDetail(UserEntity userEntity) {
        return null;
    }

    @GetMapping("/page")
    public R<IPage> getPage(IPage<UserEntity> page, UserEntity userEntity) {
        QueryWrapper<UserEntity> wrapper = new QueryWrapper<>(userEntity);
        page = userService.page(page, wrapper);
        return R.success(page);
    }

    @PostMapping("/page")
    public R<IPage> getPage2(Page<UserEntity> page, UserEntity userEntity) {
        return null;
    }

    @GetMapping("/pageDetail")
    public R<IPage> getPageDetail() {
        return null;
    }

    @PostMapping("/pageDetail")
    public R<IPage> getPageDetail2() {
        return null;
    }

    @PostMapping("exportExcel")
    public void exportExcel(@RequestBody Page<UserEntity> page, HttpServletResponse response) {
        File file = userService.createExcelFile(page);
        FileUtils.downloadFile(response, file, file.getName());
    }

    @PostMapping("importExcel")
    public R<Boolean> importExcel() {
        return R.boo(true);
    }


}

