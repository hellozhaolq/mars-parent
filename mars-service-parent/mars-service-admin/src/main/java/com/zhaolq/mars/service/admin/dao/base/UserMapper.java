package com.zhaolq.mars.service.admin.dao.base;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.zhaolq.mars.service.admin.entity.MenuEntity;
import com.zhaolq.mars.service.admin.entity.RoleEntity;
import com.zhaolq.mars.service.admin.entity.UserEntity;

import io.mybatis.mapper.BaseMapper;

/**
 * <p>
 * 用户管理 Mapper 接口
 * </p>
 *
 * @Author zhaolq
 * @Date 2020-10-29
 */
public interface UserMapper extends BaseMapper<UserEntity, String> {
    /**
     * 单个查询，携带角色列表
     *
     * @param userEntity
     */
    UserEntity selectWithRole(@Param("u") UserEntity userEntity, @Param("r") RoleEntity roleEntity);

    /**
     * 列表查询，携带角色列表
     *
     * @param userEntity
     */
    Page<UserEntity> selectPageWithRole(@Param("u") UserEntity userEntity, @Param("r") RoleEntity roleEntity);

    /**
     * 分页查询，携带角色列表，关联的嵌套Select查询(N+1查询问题)
     *
     * @param userEntity
     */
    Page<UserEntity> getPageWithRole_multipleQueries(@Param("u") UserEntity userEntity);

    /**
     * 获取权限下菜单
     *
     * @param userEntity
     */
    List<MenuEntity> selectAuthorityMenu(@Param("u") UserEntity userEntity);
}
