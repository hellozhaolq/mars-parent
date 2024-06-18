package com.zhaolq.mars.service.admin.service;

import com.zhaolq.mars.service.admin.entity.RoleEntity;
import com.zhaolq.mars.service.admin.entity.UserEntity;
import io.mybatis.service.BaseService;

import java.util.List;

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
}
