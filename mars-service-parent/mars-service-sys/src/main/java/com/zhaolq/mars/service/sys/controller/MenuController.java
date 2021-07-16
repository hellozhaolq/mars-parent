package com.zhaolq.mars.service.sys.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhaolq.mars.common.mybatis.pagination.PageConvert;
import com.zhaolq.mars.common.mybatis.pagination.WrapperBuilder;
import com.zhaolq.mars.common.valid.group.Add;
import com.zhaolq.mars.common.valid.group.Edit;
import com.zhaolq.mars.api.sys.entity.MenuEntity;
import com.zhaolq.mars.service.sys.service.IMenuService;
import com.zhaolq.mars.tool.core.lang.Assert;
import com.zhaolq.mars.tool.core.result.R;
import com.zhaolq.mars.tool.core.result.ResultCode;
import com.zhaolq.mars.tool.core.utils.ObjectUtils;
import com.zhaolq.mars.tool.core.utils.StringUtils;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * <p>
 * 菜单管理 前端控制器
 * </p>
 *
 * @author zhaolq
 * @since 2020-10-29
 */
@Validated
@Slf4j
@RestController
@RequestMapping("/menu")
@AllArgsConstructor
public class MenuController {

    private IMenuService menuService;

    @PostMapping("/post")
    @ApiOperation(value = "单个新增", notes = "单个新增")
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
    @ApiOperation(value = "单个删除", notes = "单个删除")
    public R<Boolean> delete(@PathVariable("id") @NotNull(message = "缺少id") String id) {
        MenuEntity menuEntity = menuService.getById(id);
        if (menuEntity == null) {
            return R.failureCh("菜单不存在");
        }
        boolean boo = menuService.removeById(id);
        return R.boo(boo);
    }

    @PutMapping("/put")
    @ApiOperation(value = "单个修改", notes = "单个修改")
    public R<Boolean> put(@Validated({Edit.class}) @RequestBody MenuEntity menuEntity) {
        MenuEntity menuEntityTemp = menuService.getById(menuEntity.getId());
        if (menuEntityTemp == null) {
            return R.failureCh("菜单不存在");
        }
        boolean boo = menuService.updateById(menuEntity);
        return R.boo(boo);
    }

    @GetMapping("/get")
    @ApiOperation(value = "单个查询", notes = "单个查询")
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
    @ApiOperation(value = "列表查询", notes = "列表查询")
    public R<List<MenuEntity>> getList(MenuEntity menuEntity) {
        return R.success(menuService.list(WrapperBuilder.getQueryWrapper(menuEntity)));
    }

    @GetMapping("/getPage")
    @ApiOperation(value = "分页查询", notes = "分页查询")
    public R<IPage<MenuEntity>> getPage(PageConvert<MenuEntity> pageConvert, MenuEntity menuEntity) {
        return R.success(menuService.page(pageConvert.getPagePlus(), WrapperBuilder.getQueryWrapper(menuEntity)));
    }

}

