package com.zhaolq.mars.service.sys.controller;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import com.zhaolq.mars.api.sys.entity.MenuEntity;
import com.zhaolq.mars.api.sys.entity.RoleEntity;
import com.zhaolq.mars.api.sys.entity.UserEntity;
import com.zhaolq.mars.common.mybatis.pagination.PageConvert;
import com.zhaolq.mars.common.valid.group.Add;
import com.zhaolq.mars.common.valid.group.Edit;
import com.zhaolq.mars.service.sys.service.IUserService;
import com.zhaolq.mars.tool.core.io.IoUtils;
import com.zhaolq.mars.tool.core.lang.Assert;
import com.zhaolq.mars.tool.core.result.R;
import com.zhaolq.mars.tool.core.result.ResultCode;
import com.zhaolq.mars.tool.core.utils.ObjectUtils;
import com.zhaolq.mars.tool.core.utils.StringUtils;

import cn.hutool.core.lang.tree.Tree;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 用户管理 前端控制器
 * </p>
 *
 * @author zhaolq
 * @date 2020-10-29
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
     * @return com.zhaolq.mars.api.sys.entity.UserEntity
     * @throws
     */
    @PostMapping("/post")
    @ApiOperation(value = "单个新增", notes = "单个新增")
    public R<Boolean> post(@Validated({Add.class}) @RequestBody(required = true) UserEntity userEntity) {
        // 检查用户是否存在
        QueryWrapper<UserEntity> wrapper = new QueryWrapper<>(new UserEntity().setAccount(userEntity.getAccount()));
        UserEntity result = userService.getOne(wrapper);
        if (ObjectUtils.isNotNull(result)) {
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
     * @return com.zhaolq.mars.api.sys.entity.UserEntity
     * @throws
     */
    @DeleteMapping("/delete/{id}")
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
     * @return com.zhaolq.mars.api.sys.entity.UserEntity
     * @throws
     */
    @PutMapping("/put")
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
     * @return com.zhaolq.mars.api.sys.entity.UserEntity
     * @throws
     */
    @GetMapping("/get")
    @ApiOperation(value = "单个查询", notes = "单个查询")
    public R<UserEntity> get(UserEntity userEntity) {
        // 这里永远断言成功，即使请求没有参数userEntity也不是null。
        Assert.notNull(userEntity, ResultCode.PARAM_NOT_COMPLETE.getDescCh());
        boolean condition =
                userEntity == null || (StringUtils.isEmpty(userEntity.getId()) && StringUtils.isEmpty(userEntity.getAccount()));
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

    /**
     * 列表查询
     *
     * @param userEntity
     * @return com.zhaolq.mars.tool.core.result.R<java.util.List < com.zhaolq.mars.api.sys.entity.UserEntity>>
     */
    @GetMapping("/getList")
    @ApiOperation(value = "列表查询", notes = "列表查询")
    public R<List<UserEntity>> getList(UserEntity userEntity) {
        QueryWrapper<UserEntity> wrapper = new QueryWrapper<>(userEntity);
        return R.success(userService.list(wrapper));
    }

    /**
     * get分页查询
     *
     * @param pageConvert
     * @param userEntity
     * @return com.zhaolq.mars.tool.core.result.R<com.baomidou.mybatisplus.core.metadata.IPage < com.zhaolq.mars.api.sys.entity.UserEntity>>
     */
    @GetMapping("/getPage")
    @ApiOperation(value = "分页查询", notes = "分页查询")
    public R<IPage<UserEntity>> getPage(PageConvert<UserEntity> pageConvert, UserEntity userEntity) {
        QueryWrapper<UserEntity> wrapper = new QueryWrapper<>(userEntity);
        return R.success(userService.page(pageConvert.getPagePlus(), wrapper));
    }

    /**
     * post分页查询。post请求接受两个参数，只能有一个参数从RequestBody中获取。
     *
     * @param pageConvert
     * @param userEntity
     * @return com.zhaolq.mars.tool.core.result.R<com.baomidou.mybatisplus.core.metadata.IPage < com.zhaolq.mars.api.sys.entity.UserEntity>>
     */
    @PostMapping("/getPage2")
    @ApiOperation(value = "分页查询post请求", notes = "分页查询post请求")
    public R<IPage<UserEntity>> getPage2(PageConvert<UserEntity> pageConvert,
                                         @RequestBody(required = false) UserEntity userEntity) {
        QueryWrapper<UserEntity> wrapper = new QueryWrapper<>(userEntity);
        return R.success(userService.page(pageConvert.getPagePlus(), wrapper));
    }

    /**
     * 单个查询，携带角色列表
     *
     * @param userEntity
     * @return com.zhaolq.mars.tool.core.result.R<com.zhaolq.mars.api.sys.entity.UserEntity>
     */
    @GetMapping("/getWithRole")
    @ApiOperation(value = "单个查询，携带角色列表", notes = "单个查询，携带角色列表")
    public R<UserEntity> getWithRole(UserEntity userEntity) {
        boolean condition =
                userEntity == null || (StringUtils.isEmpty(userEntity.getId()) && StringUtils.isEmpty(userEntity.getAccount()));
        if (condition) {
            return R.failure(ResultCode.PARAM_NOT_COMPLETE);
        }
        userEntity = userService.getWithRole(userEntity, null);
        if (userEntity == null) {
            return R.failure(ResultCode.USER_NOT_EXISTED);
        }
        // 角色根据ID排序
        Collections.sort(userEntity.getRoleList(),
                (r1, r2) -> Integer.valueOf(r1.getId()).compareTo(Integer.valueOf(r2.getId())));
        return R.success(userEntity);
    }

    /**
     * 列表查询，携带角色列表，关联的嵌套Select查询(N+1查询问题)
     *
     * @param userEntity
     * @return com.zhaolq.mars.tool.core.result.R<com.zhaolq.mars.api.sys.entity.UserEntity>
     */
    @GetMapping("/getWithRoleNestedSelectTest")
    @ApiOperation(value = "列表查询，携带角色列表", notes = "列表查询，携带角色列表")
    public R<UserEntity> getWithRoleNestedSelectTest(UserEntity userEntity) {
        boolean condition =
                userEntity == null || (StringUtils.isEmpty(userEntity.getId()) && StringUtils.isEmpty(userEntity.getAccount()));
        if (condition) {
            return R.failure(ResultCode.PARAM_NOT_COMPLETE);
        }
        userEntity = userService.getWithRoleNestedSelectTest(userEntity);
        if (userEntity == null) {
            return R.failure(ResultCode.USER_NOT_EXISTED);
        }
        // 角色根据ID排序
        Collections.sort(userEntity.getRoleList(),
                (r1, r2) -> Integer.valueOf(r1.getId()).compareTo(Integer.valueOf(r2.getId())));
        return R.success(userEntity);
    }

    /**
     * 列表查询，携带角色列表
     *
     * @param userEntity
     * @return com.zhaolq.mars.tool.core.result.R<java.util.List < com.zhaolq.mars.api.sys.entity.UserEntity>>
     */
    @GetMapping("/getListWithRole")
    @ApiOperation(value = "列表查询，携带角色列表", notes = "列表查询，携带角色列表")
    public R<List<UserEntity>> getListWithRole(UserEntity userEntity) {
        List<UserEntity> list = userService.listWithRole(userEntity, null);
        if (list != null) {
            for (UserEntity u : list) {
                // 角色根据ID排序(roleId转integer排序)
                Collections.sort(u.getRoleList(),
                        (r1, r2) -> Integer.valueOf(r1.getId()).compareTo(Integer.valueOf(r2.getId())));
            }
        }
        return R.success(list);
    }

    /**
     * 分页查询，携带角色列表，连表查询，多个参数
     * 仅供参考，使用关联的嵌套结果映射进行一对多分页查询时，其实是根据多方分页，会导致多方数据缺失。例如：
     * 1个user对应3个role，当分页size=2、current=1时，结果会少1个role。
     * 解决办法：
     * 1、避免一对多分页查询场景设计。
     * 2、使用关联的嵌套Select分页，但存在N+1查询问题。参考
     *
     * @param pageConvert
     * @param userEntity
     * @param roleEntity
     * @return com.zhaolq.mars.tool.core.result.R<com.baomidou.mybatisplus.core.metadata.IPage < com.zhaolq.mars.api.sys.entity.UserEntity>>
     */
    @Deprecated
    @GetMapping("/getPageWithRole")
    @ApiOperation(value = "分页查询，携带角色列表", notes = "分页查询，携带角色列表")
    public R<IPage<UserEntity>> getPageWithRole(PageConvert<UserEntity> pageConvert, UserEntity userEntity,
                                                RoleEntity roleEntity) {
        return R.success(userService.pageWithRole(pageConvert.getPagePlus(), userEntity, roleEntity));
    }

    /**
     * 分页查询，携带角色列表，关联的嵌套Select查询(N+1查询问题)
     *
     * @param pageConvert
     * @param userEntity
     * @return com.zhaolq.mars.tool.core.result.R<com.baomidou.mybatisplus.core.metadata.IPage < com.zhaolq.mars.api.sys.entity.UserEntity>>
     */
    @GetMapping("/getPageWithRoleNestedSelectTest")
    @ApiOperation(value = "分页查询，携带角色列表", notes = "分页查询，携带角色列表")
    public R<IPage<UserEntity>> getPageWithRoleNestedSelectTest(PageConvert<UserEntity> pageConvert,
                                                                UserEntity userEntity) {
        return R.success(userService.pageWithRoleNestedSelectTest(pageConvert.getPagePlus(), userEntity));
    }


    /**
     * 导出
     *
     * @param userEntity
     * @param response
     */
    @GetMapping("/getExportExcel")
    @ApiOperation(value = "导出", notes = "导出")
    public void getExportExcel(UserEntity userEntity, HttpServletResponse response) {
        QueryWrapper<UserEntity> wrapper = new QueryWrapper<>(userEntity);
        List<UserEntity> list = userService.list(wrapper);

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + "test.xlsx");

        ServletOutputStream out = null;
        try {
            out = response.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 通过工具类创建writer，默认创建xls格式
        ExcelWriter writer = ExcelUtil.getWriter(true);
        // 一次性写出内容，使用默认样式，强制输出标题
        writer.write(list, true);
        writer.flush(out, true);

        // 关闭writer，释放内存
        writer.close();
        //此处记得关闭输出Servlet流
        IoUtils.close(out);
    }

    @PostMapping("/postImportExcel")
    @ApiOperation(value = "导入", notes = "导入")
    public R<Boolean> postImportExcel() {
        return R.boo(true);
    }

    /**
     * 获取权限下菜单树
     *
     * @param userEntity
     * @return com.zhaolq.mars.tool.core.result.R<java.util.List < com.zhaolq.mars.api.sys.entity.MenuEntity>>
     */
    @GetMapping("/getAuthorityMenuTree")
    @ApiOperation(value = "获取权限下菜单树", notes = "获取权限下菜单树")
    public R<List<MenuEntity>> getAuthorityMenuTree(UserEntity userEntity) {
        List<MenuEntity> menuTreeList = userService.getAuthorityMenuTree(userEntity);
        return R.success(menuTreeList);
    }

    /**
     * 获取权限下菜单树
     *
     * @param userEntity
     * @return com.zhaolq.mars.tool.core.result.R<java.util.List < com.zhaolq.mars.api.sys.entity.MenuEntity>>
     */
    @GetMapping("/getAuthorityMenuTree2")
    @ApiOperation(value = "获取权限下菜单树", notes = "获取权限下菜单树")
    public R<List<Tree<String>>> getAuthorityMenuTree2(UserEntity userEntity) {
        List<Tree<String>> menuTreeList = userService.getAuthorityMenuTree2(userEntity);
        return R.success(menuTreeList);
    }

    @GetMapping("/testPagehelper")
    @ApiOperation(value = "测试PageHelper", notes = "测试PageHelper")
    public R<String> testPagehelper() {

        return R.success("");
    }

}

