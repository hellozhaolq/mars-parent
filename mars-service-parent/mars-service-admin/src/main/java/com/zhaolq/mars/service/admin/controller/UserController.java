package com.zhaolq.mars.service.admin.controller;

import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.mybatis.spring.SqlSessionTemplate;
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

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageParam;
import com.zhaolq.mars.common.core.exception.BaseRuntimeException;
import com.zhaolq.mars.common.core.result.ErrorEnum;
import com.zhaolq.mars.common.core.result.R;
import com.zhaolq.mars.common.valid.group.Add;
import com.zhaolq.mars.common.valid.group.Edit;
import com.zhaolq.mars.service.admin.entity.UserEntity;
import com.zhaolq.mars.service.admin.service.IUserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterStyle;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 用户管理 前端控制器
 * </p>
 *
 * @Author zhaolq
 * @Date 2020-10-29
 */
@Validated
@Slf4j
@RestController
@Tag(name = "用户模块", description = "用户模块")
@RequestMapping(path = "/user", consumes = {MediaType.ALL_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
public class UserController {
    private final SqlSessionTemplate sqlSessionTemplate;
    private IUserService userService;

    /**
     * 单个查询
     *
     * @param userEntity
     * @return
     */
    @GetMapping("/get")
    @Operation(summary = "单个查询", description = "单个查询")
    public R<UserEntity> get(UserEntity userEntity) {
        // 这里永远断言成功，即使请求没有参数userEntity也不是null。
        Validate.notNull(userEntity, ErrorEnum.PARAM_NOT_COMPLETE.getMsg());
        boolean condition = userEntity == null || (StringUtils.isEmpty(userEntity.getId())
                && StringUtils.isEmpty(userEntity.getAccount()));
        if (condition) {
            throw new BaseRuntimeException(ErrorEnum.PARAM_ERROR);
        }
        UserEntity entity = userService.findOne(userEntity);
        if (ObjectUtils.isEmpty(entity)) {
            throw new BaseRuntimeException(ErrorEnum.USER_NOT_EXISTED);
        }
        return R.success(entity);
    }

    /**
     * 单个新增
     *
     * @param userEntity
     * @return
     */
    @PostMapping("/post")
    @Operation(summary = "单个新增", description = "单个新增")
    public R<UserEntity> post(@Validated({Add.class}) @RequestBody(required = true) UserEntity userEntity) {
        // 检查用户是否存在
        UserEntity entity = userService.findOne(new UserEntity().setAccount(userEntity.getAccount()));
        if (!ObjectUtils.isEmpty(entity)) {
            throw new BaseRuntimeException(ErrorEnum.USER_HAS_EXISTED);
        }
        // 不存在，则新增
        UserEntity savedEntity = userService.save(userEntity);
        return R.success(ObjectUtils.isNotEmpty(savedEntity) ? savedEntity : null);
    }

    /**
     * 单个修改
     *
     * @param userEntity
     * @return
     */
    @PutMapping("/put")
    @Operation(summary = "单个修改", description = "单个修改")
    public R<UserEntity> put(@Validated({Edit.class}) @RequestBody UserEntity userEntity) {
        UserEntity entity = userService.updateSelective(userEntity);
        if (ObjectUtils.isEmpty(entity)) {
            throw new BaseRuntimeException(ErrorEnum.USER_NOT_EXISTED);
        }
        return R.success(entity);
    }

    /**
     * 单个删除
     *
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    @Parameter(name = "id", description = "用户id", required = true, style = ParameterStyle.SIMPLE)
    @Operation(summary = "单个删除", description = "单个删除")
    public R<Boolean> delete(@PathVariable("id") @NotNull(message = "缺少id") String id) {
        UserEntity userEntity = userService.findById(id);
        if (userEntity == null) {
            throw new BaseRuntimeException(ErrorEnum.USER_NOT_EXISTED);
        }
        int result = userService.deleteById(id);
        return R.boo(result == 1 ? Boolean.TRUE : Boolean.FALSE);
    }

    /**
     * 列表查询
     *
     * @param userEntity
     * @return
     */
    @GetMapping("/getList")
    @Operation(summary = "列表查询", description = "列表查询")
    public R<List<UserEntity>> getList(UserEntity userEntity) {
        return R.success(userService.findList(userEntity));
    }

    /**
     * get分页查询
     *
     * @param userEntity
     * @return
     */
    @GetMapping("/getPage")
    @Operation(summary = "分页查询", description = "分页查询")
    public R<PageInfo<UserEntity>> getPage(UserEntity userEntity, PageParam pageParam) {
        PageHelper.startPage(pageParam.getPageNum(), pageParam.getPageSize());
        List<UserEntity> list = userService.findList(userEntity);
        PageInfo pageInfo = new PageInfo(list);
        return R.success(pageInfo);
    }

//
//    /**
//     * post分页查询。post请求接受两个参数，只能有一个参数从RequestBody中获取。
//     *
//     * @param pageConvert
//     * @param userEntity
//     * @return com.zhaolq.mars.tool.core.result.R<com.baomidou.mybatisplus.core.metadata.IPage < com.zhaolq.mars.api.sys.entity.UserEntity>>
//     */
//    @PostMapping("/getPage2")
//    @Operation(summary = "分页查询post请求", description = "分页查询post请求")
//    public R<IPage<UserEntity>> getPage2(
//            PageConvert<UserEntity> pageConvert,
//            @RequestBody(required = false) UserEntity userEntity) {
//        QueryWrapper<UserEntity> wrapper = new QueryWrapper<>(userEntity);
//        return R.success(userService.page(pageConvert.getPagePlus(), wrapper));
//    }
//
//    /**
//     * 单个查询，携带角色列表
//     *
//     * @param userEntity
//     * @return com.zhaolq.mars.tool.core.result.R<com.zhaolq.mars.api.sys.entity.UserEntity>
//     */
//    @GetMapping("/getWithRole")
//    @Operation(summary = "单个查询，携带角色列表", description = "单个查询，携带角色列表")
//    public R<UserEntity> getWithRole(UserEntity userEntity) {
//        boolean condition =
//                userEntity == null || (StringUtils.isEmpty(userEntity.getId()) && StringUtils.isEmpty(userEntity.getAccount()));
//        if (condition) {
//            return R.failure(iError.PARAM_NOT_COMPLETE);
//        }
//        userEntity = userService.getWithRole(userEntity, null);
//        if (userEntity == null) {
//            return R.failure(iError.USER_NOT_EXISTED);
//        }
//        // 角色根据ID排序
//        Collections.sort(userEntity.getRoleList(),
//                (r1, r2) -> Integer.valueOf(r1.getId()).compareTo(Integer.valueOf(r2.getId())));
//        return R.success(userEntity);
//    }
//
//    /**
//     * 列表查询，携带角色列表，关联的嵌套Select查询(N+1查询问题)
//     *
//     * @param userEntity
//     * @return com.zhaolq.mars.tool.core.result.R<com.zhaolq.mars.api.sys.entity.UserEntity>
//     */
//    @GetMapping("/getWithRoleNestedSelectTest")
//    @Operation(summary = "列表查询，携带角色列表", description = "列表查询，携带角色列表")
//    public R<UserEntity> getWithRoleNestedSelectTest(UserEntity userEntity) {
//        boolean condition =
//                userEntity == null || (StringUtils.isEmpty(userEntity.getId()) && StringUtils.isEmpty(userEntity.getAccount()));
//        if (condition) {
//            return R.failure(iError.PARAM_NOT_COMPLETE);
//        }
//        userEntity = userService.getWithRoleNestedSelectTest(userEntity);
//        if (userEntity == null) {
//            return R.failure(iError.USER_NOT_EXISTED);
//        }
//        // 角色根据ID排序
//        Collections.sort(userEntity.getRoleList(),
//                (r1, r2) -> Integer.valueOf(r1.getId()).compareTo(Integer.valueOf(r2.getId())));
//        return R.success(userEntity);
//    }
//
//    /**
//     * 列表查询，携带角色列表
//     *
//     * @param userEntity
//     * @return com.zhaolq.mars.tool.core.result.R<java.util.List < com.zhaolq.mars.api.sys.entity.UserEntity>>
//     */
//    @GetMapping("/getListWithRole")
//    @Operation(summary = "列表查询，携带角色列表", description = "列表查询，携带角色列表")
//    public R<List<UserEntity>> getListWithRole(UserEntity userEntity) {
//        List<UserEntity> list = userService.listWithRole(userEntity, null);
//        if (list != null) {
//            for (UserEntity u : list) {
//                // 角色根据ID排序(roleId转integer排序)
//                Collections.sort(u.getRoleList(),
//                        (r1, r2) -> Integer.valueOf(r1.getId()).compareTo(Integer.valueOf(r2.getId())));
//            }
//        }
//        return R.success(list);
//    }
//
//    /**
//     * 分页查询，携带角色列表，连表查询，多个参数
//     * 仅供参考，使用关联的嵌套结果映射进行一对多分页查询时，其实是根据多方分页，会导致多方数据缺失。例如：
//     * 1个user对应3个role，当分页size=2、current=1时，结果会少1个role。
//     * 解决办法：
//     * 1、避免一对多分页查询场景设计。
//     * 2、使用关联的嵌套Select分页，但存在N+1查询问题。参考
//     *
//     * @param pageConvert
//     * @param userEntity
//     * @param roleEntity
//     * @return com.zhaolq.mars.tool.core.result.R<com.baomidou.mybatisplus.core.metadata.IPage < com.zhaolq.mars.api.sys.entity.UserEntity>>
//     */
//    @Deprecated
//    @GetMapping("/getPageWithRole")
//    @Operation(summary = "分页查询，携带角色列表", description = "分页查询，携带角色列表")
//    public R<IPage<UserEntity>> getPageWithRole(
//            PageConvert<UserEntity> pageConvert, UserEntity userEntity,
//            RoleEntity roleEntity) {
//        return R.success(userService.pageWithRole(pageConvert.getPagePlus(), userEntity, roleEntity));
//    }
//
//    /**
//     * 分页查询，携带角色列表，关联的嵌套Select查询(N+1查询问题)
//     *
//     * @param pageConvert
//     * @param userEntity
//     * @return com.zhaolq.mars.tool.core.result.R<com.baomidou.mybatisplus.core.metadata.IPage < com.zhaolq.mars.api.sys.entity.UserEntity>>
//     */
//    @GetMapping("/getPageWithRoleNestedSelectTest")
//    @Operation(summary = "分页查询，携带角色列表", description = "分页查询，携带角色列表")
//    public R<IPage<UserEntity>> getPageWithRoleNestedSelectTest(
//            PageConvert<UserEntity> pageConvert,
//            UserEntity userEntity) {
//        return R.success(userService.pageWithRoleNestedSelectTest(pageConvert.getPagePlus(), userEntity));
//    }
//
//
//    /**
//     * 导出
//     *
//     * @param userEntity
//     * @param response
//     */
//    @GetMapping("/getExportExcel")
//    @Operation(summary = "导出", description = "导出")
//    public void getExportExcel(Page<UserEntity> pageConvert, UserEntity userEntity, HttpServletResponse response) {
//        QueryWrapper<UserEntity> wrapper = new QueryWrapper<>(userEntity);
//        IPage<UserEntity> iPage = userService.page(pageConvert, wrapper);
//        List<UserEntity> list = iPage.getRecords();
//        System.out.println(list.get(0));
//        System.out.println(list.size());
//
//        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
//        response.setHeader("Content-Disposition", "attachment;filename=" + "test.xlsx");
//
//        ServletOutputStream out = null;
//        try {
//            out = response.getOutputStream();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        // out.write();
//
//        //此处记得关闭输出Servlet流
//        IoUtil.close(out);
//    }
//
//    @PostMapping("/postImportExcel")
//    @Operation(summary = "导入", description = "导入")
//    public R<Boolean> postImportExcel() {
//        return R.boo(true);
//    }
//
//    /**
//     * 获取权限下菜单树
//     *
//     * @param userEntity
//     * @return com.zhaolq.mars.tool.core.result.R<java.util.List < com.zhaolq.mars.api.sys.entity.MenuEntity>>
//     */
//    @GetMapping("/getAuthorityMenuTree")
//    @Operation(summary = "获取权限下菜单树", description = "获取权限下菜单树")
//    public R<List<MenuEntity>> getAuthorityMenuTree(UserEntity userEntity) {
//        List<MenuEntity> menuTreeList = userService.getAuthorityMenuTree(userEntity);
//        return R.success(menuTreeList);
//    }
//
//    @GetMapping("/testPagehelper")
//    @Operation(summary = "测试PageHelper", description = "测试PageHelper")
//    public R<String> testPagehelper() {
//
//        return R.success("");
//    }

}

