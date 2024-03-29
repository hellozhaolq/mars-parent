package com.zhaolq.mars.service.admin.service;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import com.zhaolq.mars.api.admin.entity.MenuEntity;
import com.zhaolq.mars.api.admin.entity.RoleEntity;
import com.zhaolq.mars.api.admin.entity.UserEntity;

/**
 * <p>
 * 用户管理 服务类
 * </p>
 *
 * @author zhaolq
 * @date 2020-10-29
 */
public interface IUserService extends IService<UserEntity> {

    /**
     * 单个查询，携带角色列表
     *
     * @param userEntity
     * @return com.zhaolq.mars.api.sys.entity.UserEntity
     */
    UserEntity getWithRole(UserEntity userEntity, RoleEntity roleEntity);

    /**
     * 列表查询，携带角色列表，关联的嵌套Select查询(N+1查询问题)
     *
     * @param userEntity
     * @return com.zhaolq.mars.api.sys.entity.UserEntity
     */
    UserEntity getWithRoleNestedSelectTest(UserEntity userEntity);

    /**
     * 列表查询，携带角色列表
     *
     * @param userEntity
     * @return java.util.List<com.zhaolq.mars.api.sys.entity.UserEntity>
     */
    List<UserEntity> listWithRole(UserEntity userEntity, RoleEntity roleEntity);

    /**
     * 分页查询，携带角色列表，连表查询，多个参数
     *
     * @param page
     * @param userEntity
     * @param roleEntity
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.zhaolq.mars.api.sys.entity.UserEntity>
     */
    @Deprecated
    IPage<UserEntity> pageWithRole(IPage<UserEntity> page, UserEntity userEntity, RoleEntity roleEntity);

    /**
     * 分页查询，携带角色列表，关联的嵌套Select查询(N+1查询问题)
     *
     * @param page
     * @param userEntity
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.zhaolq.mars.api.sys.entity.UserEntity>
     */
    IPage<UserEntity> pageWithRoleNestedSelectTest(IPage<UserEntity> page, UserEntity userEntity);

    /**
     * 获取权限下菜单树
     *
     * @param userEntity
     * @return java.util.Set<com.zhaolq.mars.api.sys.entity.MenuEntity>
     */
    List<MenuEntity> getAuthorityMenuTree(UserEntity userEntity);

}
