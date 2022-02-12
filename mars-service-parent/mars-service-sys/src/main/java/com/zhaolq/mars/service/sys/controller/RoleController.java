package com.zhaolq.mars.service.sys.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhaolq.mars.common.mybatis.pagination.PageConvert;
import com.zhaolq.mars.common.mybatis.pagination.WrapperBuilder;
import com.zhaolq.mars.common.valid.group.Add;
import com.zhaolq.mars.common.valid.group.Edit;
import com.zhaolq.mars.api.sys.entity.RoleEntity;
import com.zhaolq.mars.service.sys.service.IRoleService;
import com.zhaolq.mars.tool.core.lang.Assert;
import com.zhaolq.mars.tool.core.result.R;
import com.zhaolq.mars.tool.core.result.ResultCode;
import com.zhaolq.mars.tool.core.utils.ObjectUtils;
import com.zhaolq.mars.tool.core.utils.StringUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * <p>
 * 角色管理 前端控制器
 * </p>
 *
 * @author zhaolq
 * @date 2020-10-29
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private IRoleService roleService;

    @PostMapping("/post")
    @ApiOperation(value = "单个新增", notes = "单个新增")
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
    @ApiOperation(value = "单个删除", notes = "单个删除")
    public R<Boolean> delete(@PathVariable("id") @NotNull(message = "缺少id") String id) {
        RoleEntity roleEntity = roleService.getById(id);
        if (roleEntity == null) {
            return R.failureCh("角色不存在");
        }
        boolean boo = roleService.removeById(id);
        return R.boo(boo);
    }

    @PutMapping("/put")
    @ApiOperation(value = "单个修改", notes = "单个修改")
    public R<Boolean> put(@Validated({Edit.class}) @RequestBody RoleEntity roleEntity) {
        RoleEntity roleEntityTemp = roleService.getById(roleEntity.getId());
        if (roleEntityTemp == null) {
            return R.failureCh("角色不存在");
        }
        boolean boo = roleService.updateById(roleEntity);
        return R.boo(boo);
    }

    @GetMapping("/get")
    @ApiOperation(value = "单个查询", notes = "单个查询")
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
    @ApiOperation(value = "列表查询", notes = "列表查询")
    public R<List<RoleEntity>> getList(RoleEntity roleEntity) {
        return R.success(roleService.list(WrapperBuilder.getQueryWrapper(roleEntity)));
    }

    @GetMapping("/getPage")
    @ApiOperation(value = "分页查询", notes = "分页查询")
    public R<IPage<RoleEntity>> getPage(PageConvert<RoleEntity> pageConvert, RoleEntity roleEntity) {
        return R.success(roleService.page(pageConvert.getPagePlus(), WrapperBuilder.getQueryWrapper(roleEntity)));
    }

}

