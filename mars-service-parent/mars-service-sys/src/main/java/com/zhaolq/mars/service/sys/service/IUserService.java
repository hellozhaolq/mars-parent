package com.zhaolq.mars.service.sys.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhaolq.mars.service.sys.entity.MenuEntity;
import com.zhaolq.mars.service.sys.entity.RoleEntity;
import com.zhaolq.mars.service.sys.entity.UserEntity;

import java.io.File;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 用户管理 服务类
 * </p>
 *
 * @author zhaolq
 * @since 2020-10-29
 */
public interface IUserService extends IService<UserEntity> {

    /**
     * 单个查询，携带角色列表
     *
     * @param userEntity
     * @return com.zhaolq.mars.service.sys.entity.UserEntity
     */
    UserEntity getWithRole(UserEntity userEntity, RoleEntity roleEntity);

    /**
     * 列表查询，携带角色列表
     *
     * @param userEntity
     * @return java.util.List<com.zhaolq.mars.service.sys.entity.UserEntity>
     */
    List<UserEntity> getWithRoleList(UserEntity userEntity, RoleEntity roleEntity);

    /**
     * 分页查询，携带角色列表，连表查询，多个参数
     *
     * @param page
     * @param userEntity
     * @param roleEntity
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.zhaolq.mars.service.sys.entity.UserEntity>
     */
    IPage<UserEntity> getWithRolePage(IPage<UserEntity> page, UserEntity userEntity, RoleEntity roleEntity);

    /**
     * 获取权限下菜单树
     *
     * @param userEntity
     * @return java.util.Set<com.zhaolq.mars.service.sys.entity.MenuEntity>
     */
    List<MenuEntity> getAuthorityMenuTree(UserEntity userEntity);

}
