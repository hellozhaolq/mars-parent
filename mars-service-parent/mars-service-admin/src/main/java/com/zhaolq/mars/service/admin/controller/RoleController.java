package com.zhaolq.mars.service.admin.controller;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.IPage;

import com.zhaolq.mars.api.admin.entity.RoleEntity;
import com.zhaolq.mars.common.mybatis.pagination.PageConvert;
import com.zhaolq.mars.common.mybatis.pagination.WrapperBuilder;
import com.zhaolq.mars.common.valid.group.Add;
import com.zhaolq.mars.common.valid.group.Edit;
import com.zhaolq.mars.service.admin.service.IRoleService;
import com.zhaolq.mars.tool.core.lang.Assert;
import com.zhaolq.mars.tool.core.result.R;
import com.zhaolq.mars.tool.core.result.ResultCode;
import com.zhaolq.mars.tool.core.utils.ObjectUtils;
import com.zhaolq.mars.tool.core.utils.StringUtils;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterStyle;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * <p>
 * 角色管理 前端控制器
 * </p>
 *
 * @author zhaolq
 * @date 2020-10-29
 */
@RestController
@Tag(name = "角色模块", description = "角色模块")
@RequestMapping(path = "/role", produces = {MediaType.ALL_VALUE})
public class RoleController {

    @Autowired
    private IRoleService roleService;

    @PostMapping("/post")
    @Operation(summary = "单个新增", description = "单个新增")
    public R<Boolean> post(@Validated({Add.class}) @RequestBody(required = true) RoleEntity roleEntity) {
        // 检查角色code是否存在
        RoleEntity result = roleService.getOne(WrapperBuilder.getQueryWrapper(new RoleEntity().setCode(roleEntity.getCode())));

        if (ObjectUtils.isNotNull(result)) {
            return R.failureCh("角色已存在");
        }
        // 不存在，则新增
        boolean boo = roleService.save(roleEntity);
        return R.boo(boo);
    }

    @DeleteMapping("/delete/{id}")
    @Parameter(name = "id", description = "角色id", required = true, style = ParameterStyle.SIMPLE)
    @Operation(summary = "单个删除", description = "单个删除")
    public R<Boolean> delete(@PathVariable("id") @NotNull(message = "缺少id") String id) {
        RoleEntity roleEntity = roleService.getById(id);
        if (roleEntity == null) {
            return R.failureCh("角色不存在");
        }
        boolean boo = roleService.removeById(id);
        return R.boo(boo);
    }

    @PutMapping("/put")
    @Operation(summary = "单个修改", description = "单个修改")
    public R<Boolean> put(@Validated({Edit.class}) @RequestBody RoleEntity roleEntity) {
        RoleEntity roleEntityTemp = roleService.getById(roleEntity.getId());
        if (roleEntityTemp == null) {
            return R.failureCh("角色不存在");
        }
        boolean boo = roleService.updateById(roleEntity);
        return R.boo(boo);
    }

    @GetMapping("/get")
    @Operation(summary = "单个查询", description = "单个查询")
    public R<RoleEntity> get(RoleEntity roleEntity) {
        // 这里永远断言成功，即使请求没有参数userEntity也不是null。
        Assert.notNull(roleEntity, ResultCode.PARAM_NOT_COMPLETE.getDescCh());
        boolean condition = roleEntity == null || (StringUtils.isEmpty(roleEntity.getId()) && StringUtils.isEmpty(roleEntity.getCode()));
        if (condition) {
            return R.failure(ResultCode.PARAM_NOT_COMPLETE);
        }
        roleEntity = roleService.getOne(WrapperBuilder.getQueryWrapper(roleEntity));
        if (roleEntity == null) {
            return R.failureCh("角色不存在");
        }
        return R.success(roleEntity);
    }

    @GetMapping("/getList")
    @Operation(summary = "列表查询", description = "列表查询")
    public R<List<RoleEntity>> getList(RoleEntity roleEntity) {
        return R.success(roleService.list(WrapperBuilder.getQueryWrapper(roleEntity)));
    }

    @GetMapping("/getPage")
    @Operation(summary = "分页查询", description = "分页查询")
    public R<IPage<RoleEntity>> getPage(PageConvert<RoleEntity> pageConvert, RoleEntity roleEntity) {
        return R.success(roleService.page(pageConvert.getPagePlus(), WrapperBuilder.getQueryWrapper(roleEntity)));
    }

}
