package com.zhaolq.mars.service.admin.controller;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
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
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;

import com.zhaolq.mars.api.admin.entity.MenuEntity;
import com.zhaolq.mars.common.core.result.R;
import com.zhaolq.mars.common.core.result.ResultCode;
import com.zhaolq.mars.common.mybatis.pagination.PageConvert;
import com.zhaolq.mars.common.mybatis.pagination.WrapperBuilder;
import com.zhaolq.mars.common.valid.group.Add;
import com.zhaolq.mars.common.valid.group.Edit;
import com.zhaolq.mars.service.admin.service.IMenuService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterStyle;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 菜单管理 前端控制器
 * </p>
 *
 * @author zhaolq
 * @date 2020-10-29
 */
@Validated
@Slf4j
@RestController
@Tag(name = "菜单模块", description = "菜单模块")
@RequestMapping(path = "/menu", consumes = {MediaType.ALL_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
public class MenuController {

    private IMenuService menuService;

    @PostMapping("/post")
    @Operation(summary = "单个新增", description = "单个新增")
    public R<Boolean> post(@Validated({Add.class}) @RequestBody(required = true) MenuEntity menuEntity) {
        // 检查菜单code是否存在
        MenuEntity result = menuService.getOne(WrapperBuilder.getQueryWrapper(new MenuEntity().setCode(menuEntity.getCode())));

        if (ObjectUtils.isNotNull(result)) {
            return R.failureCh("菜单已存在");
        }
        // 不存在，则新增
        boolean boo = menuService.save(menuEntity);
        return R.boo(boo);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "单个删除", description = "单个删除")
    @Parameter(name = "id", description = "菜单id", required = true, style = ParameterStyle.SIMPLE)
    public R<Boolean> delete(@PathVariable("id") @NotNull(message = "缺少id") String id) {
        MenuEntity menuEntity = menuService.getById(id);
        if (menuEntity == null) {
            return R.failureCh("菜单不存在");
        }
        boolean boo = menuService.removeById(id);
        return R.boo(boo);
    }

    @PutMapping("/put")
    @Operation(summary = "单个修改", description = "单个修改")
    public R<Boolean> put(@Validated({Edit.class}) @RequestBody MenuEntity menuEntity) {
        MenuEntity menuEntityTemp = menuService.getById(menuEntity.getId());
        if (menuEntityTemp == null) {
            return R.failureCh("菜单不存在");
        }
        boolean boo = menuService.updateById(menuEntity);
        return R.boo(boo);
    }

    @GetMapping("/get")
    @Operation(summary = "单个查询", description = "单个查询")
    public R<MenuEntity> get(MenuEntity menuEntity) {
        // 这里永远断言成功，即使请求没有参数userEntity也不是null。
        Assert.notNull(menuEntity, ResultCode.PARAM_NOT_COMPLETE.getDescCh());
        boolean condition = menuEntity == null || (StringUtils.isEmpty(menuEntity.getId()) && StringUtils.isEmpty(menuEntity.getCode()));
        if (condition) {
            return R.failure(ResultCode.PARAM_NOT_COMPLETE);
        }
        menuEntity = menuService.getOne(WrapperBuilder.getQueryWrapper(menuEntity));
        if (menuEntity == null) {
            return R.failureCh("菜单不存在");
        }
        return R.success(menuEntity);
    }

    @GetMapping("/getList")
    @Operation(summary = "列表查询", description = "列表查询")
    public R<List<MenuEntity>> getList(MenuEntity menuEntity) {
        return R.success(menuService.list(WrapperBuilder.getQueryWrapper(menuEntity)));
    }

    @GetMapping("/getPage")
    @Operation(summary = "分页查询", description = "分页查询")
    public R<IPage<MenuEntity>> getPage(PageConvert<MenuEntity> pageConvert, MenuEntity menuEntity) {
        return R.success(menuService.page(pageConvert.getPagePlus(), WrapperBuilder.getQueryWrapper(menuEntity)));
    }

}

