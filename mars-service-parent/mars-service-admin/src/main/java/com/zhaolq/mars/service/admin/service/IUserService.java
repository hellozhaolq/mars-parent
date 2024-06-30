package com.zhaolq.mars.service.admin.service;

import java.util.List;

import com.github.pagehelper.Page;
import com.zhaolq.mars.service.admin.entity.MenuEntity;
import com.zhaolq.mars.service.admin.entity.RoleEntity;
import com.zhaolq.mars.service.admin.entity.UserEntity;

import io.mybatis.service.BaseService;

/**
 * <p>
 * 用户管理 服务类
 * </p>
 *
 * @Author zhaolq
 * @Date 2020-10-29
 */
public interface IUserService extends BaseService<UserEntity, String> {

    /**
     * 单个查询，携带角色列表
     *
     * @param userEntity
     */
    UserEntity getWithRole(UserEntity userEntity, RoleEntity roleEntity);

    /**
     * 单个查询，携带角色列表
     *
     * @param userEntity
     */
    Page<UserEntity> getPageWithRole(UserEntity userEntity);

    /**
     * 分页查询，携带角色列表，关联的嵌套Select查询(N+1查询问题)
     *
     * @param userEntity
     */
    Page<UserEntity> getPageWithRole_multipleQueries(UserEntity userEntity);

    /**
     * 获取权限下菜单树
     *
     * @param userEntity
     */
    List<MenuEntity> getAuthorityMenuTree(UserEntity userEntity);
}
