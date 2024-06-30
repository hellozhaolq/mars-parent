package com.zhaolq.mars.service.admin.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
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

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageParam;
import com.zhaolq.mars.common.core.exception.BaseRuntimeException;
import com.zhaolq.mars.common.core.result.ErrorEnum;
import com.zhaolq.mars.common.core.result.R;
import com.zhaolq.mars.common.valid.group.Add;
import com.zhaolq.mars.common.valid.group.Edit;
import com.zhaolq.mars.service.admin.entity.MenuEntity;
import com.zhaolq.mars.service.admin.entity.UserEntity;
import com.zhaolq.mars.service.admin.service.IUserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterStyle;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
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
    private IUserService userService;

    /**
     * 单个查询
     *
     * @param userEntity
     */
    @GetMapping("/get")
    @Operation(summary = "单个查询", description = "单个查询")
    public R<UserEntity> get(UserEntity userEntity) {
        // 这里永远断言成功，即使请求没有参数userEntity也不是null。
        Validate.notNull(userEntity, ErrorEnum.PARAM_NOT_COMPLETE.getMsg());
        if (ObjectUtils.isEmpty(userEntity) || (StringUtils.isAllEmpty(userEntity.getId(), userEntity.getAccount()))) {
            throw new BaseRuntimeException(ErrorEnum.PARAM_NOT_COMPLETE);
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
     * get分页查询
     *
     * @param userEntity
     */
    @GetMapping("/getPage")
    @Operation(summary = "分页查询", description = "分页查询")
    public R<PageInfo<UserEntity>> getPage(UserEntity userEntity, PageParam pageParam) {
        // 分页方式一，实测RowBounds也是物理分页
        // Example<UserEntity> example = new Example<>();
        // example.createCriteria().andEqualTo(Fn.field(UserEntity.class, "sex"), "1");
        // List<UserEntity> list = userEntity.baseMapper().selectByExample(example, new RowBounds(10, 3));

        // 分页方式二，推荐
        PageHelper.startPage(pageParam.getPageNum(), pageParam.getPageSize());
        List<UserEntity> list = userService.findList(userEntity);
        return R.success(new PageInfo(list));
    }

    /**
     * 单个查询，携带角色列表
     *
     * @param userEntity
     */
    @GetMapping("/getWithRole")
    @Operation(summary = "单个查询，携带角色列表", description = "单个查询，携带角色列表")
    public R<UserEntity> getWithRole(UserEntity userEntity) {
        if (ObjectUtils.isEmpty(userEntity) || (StringUtils.isAllEmpty(userEntity.getId(), userEntity.getAccount()))) {
            throw new BaseRuntimeException(ErrorEnum.PARAM_NOT_COMPLETE);
        }
        userEntity = userService.getWithRole(userEntity, null);
        if (userEntity == null) {
            throw new BaseRuntimeException(ErrorEnum.USER_NOT_EXISTED);
        }
        // 角色根据ID降序
        Collections.sort(userEntity.getRoleList(), (r1, r2) -> -Integer.valueOf(r1.getId()).compareTo(Integer.valueOf(r2.getId())));
        return R.success(userEntity);
    }

    /**
     * 分页查询，携带角色列表，连表查询，多个参数
     * 仅供参考，使用关联的嵌套结果映射进行一对多分页查询时，其实是根据多方分页，会导致多方数据缺失。例如：
     * 1个user对应3个role，当分页pageSize=2、pageNum=1时，结果会少1个role。
     * 解决办法：
     * 1、避免一对多分页查询场景设计。
     * 2、使用关联的嵌套Select分页，但存在N+1查询问题。参考 getPageWithRole_multipleQueries 接口
     *
     * @param userEntity
     */
    @Deprecated
    @PostMapping("/getPageWithRole")
    @Operation(summary = "分页查询，携带角色列表", description = "分页查询，携带角色列表")
    public R<PageInfo<UserEntity>> getPageWithRole(@RequestBody(required = false) UserEntity userEntity) {
        PageHelper.startPage(userEntity.getPageNum(), userEntity.getPageSize());
        Page<UserEntity> list = userService.getPageWithRole(userEntity);
        if (list != null) {
            for (UserEntity u : list) {
                // 角色根据ID降序
                Collections.sort(u.getRoleList(), (r1, r2) -> -Integer.valueOf(r1.getId()).compareTo(Integer.valueOf(r2.getId())));
            }
        }
        PageInfo pageInfo = new PageInfo(list);
        return R.success(pageInfo);
    }

    /**
     * 分页查询，携带角色列表，关联的嵌套Select查询(N+1查询问题)
     *
     * @param userEntity
     */
    @PostMapping("/getPageWithRole_multipleQueries")
    @Operation(summary = "分页查询，携带角色列表", description = "分页查询，携带角色列表")
    public R<PageInfo<UserEntity>> getPageWithRoleNestedSelectTest(@RequestBody(required = false) UserEntity userEntity) {
        PageHelper.startPage(userEntity.getPageNum(), userEntity.getPageSize());
        Page<UserEntity> list = userService.getPageWithRole_multipleQueries(userEntity);
        if (ObjectUtils.isNotEmpty(list)) {
            list.forEach(u -> {
                // 角色根据ID降序
                Collections.sort(u.getRoleList(), (r1, r2) -> -Integer.valueOf(r1.getId()).compareTo(Integer.valueOf(r2.getId())));
            });
        }
        return R.success(new PageInfo(list));
    }


    /**
     * 文件下载。
     * https://pdai.tech/md/spring/springboot/springboot-x-file-excel-poi.html
     *
     * @param userEntity
     * @param response
     */
    @PostMapping("/excelDownload")
    @Operation(summary = "导出", description = "导出")
    public void excelDownload(@RequestBody(required = false) UserEntity userEntity, HttpServletResponse response) {
        int rows = 0;
        int cols = 0;

        response.reset();
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8"); // 2007版
        response.setHeader("Content-disposition", "attachment;filename=user_excel_" + System.currentTimeMillis() + ".xlsx");

        try (ServletOutputStream outputStream = response.getOutputStream();) {
            // 写入一个空的excel，只有表头
            SXSSFWorkbook workbook = new SXSSFWorkbook();
            Sheet sheet = workbook.createSheet("第一页");
            // 表头
            List<String> fieldList = Arrays.stream(userEntity.getClass().getDeclaredFields()).map(field -> field.getName()).collect(Collectors.toList());
            String[] columns = fieldList.toArray(new String[fieldList.size()]);
            Row head = sheet.createRow(rows++);
            for (int i = 0; i < columns.length; ++i) {
                head.createCell(cols++).setCellValue(columns[i]);
            }

            // 计算循环查询的次数，即总页数
            long totalCount = userService.count(userEntity);
            int pageSize = 20000; // 每次查询的行数
            int totalPage = Math.toIntExact(totalCount % pageSize == 0 ? (totalCount / pageSize) : (totalCount / pageSize + 1)); //循环查询次数
            int currentPage = 0;

            while (true) {
                if (++currentPage > totalPage) {
                    workbook.write(outputStream);
                    workbook.dispose();
                    break;
                }
                PageHelper.startPage(currentPage, pageSize);
                List<UserEntity> list = userService.findList(userEntity);

                // 表内容
                for (UserEntity user : list) {
                    cols = 0;
                    Row row = sheet.createRow(rows++);
                    row.createCell(cols++).setCellValue(user.getId());
                    row.createCell(cols++).setCellValue(user.getAccount());
                    row.createCell(cols++).setCellValue(user.getName());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/excelUpload")
    @Operation(summary = "导入", description = "导入")
    public R<Boolean> postImportExcel() {
        return R.boo(true);
    }

    /**
     * 获取权限下菜单树
     *
     * @param userEntity
     */
    @GetMapping("/getAuthorityMenuTree")
    @Operation(summary = "获取权限下菜单树", description = "获取权限下菜单树")
    public R<List<MenuEntity>> getAuthorityMenuTree(UserEntity userEntity) {
        List<MenuEntity> menuTreeList = userService.getAuthorityMenuTree(userEntity);
        return R.success(menuTreeList);
    }
}

