package com.zhaolq.mars.service.admin.dao.base;

import com.zhaolq.mars.service.admin.entity.RoleEntity;
import com.zhaolq.mars.service.admin.entity.UserEntity;
import io.mybatis.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 用户管理 Mapper 接口
 * </p>
 *
 * @Author zhaolq
 * @Date 2020-10-29
 */
@Transactional(rollbackFor = Exception.class)
public interface UserMapper extends BaseMapper<UserEntity, String> {
    /**
     * 单个查询，携带角色列表
     *
     * @param userEntity
     * @return com.zhaolq.mars.api.sys.entity.UserEntity
     */
    UserEntity selectWithRole(@Param("u") UserEntity userEntity, @Param("r") RoleEntity roleEntity);

    /**
     * 列表查询，携带角色列表，关联的嵌套Select查询(N+1查询问题)
     *
     * @param userEntity
     * @return com.zhaolq.mars.api.sys.entity.UserEntity
     */
    UserEntity selectWithRoleNestedSelectTest(@Param("u") UserEntity userEntity);

    /**
     * 列表查询，携带角色列表
     *
     * @param userEntity
     * @return java.util.List<com.zhaolq.mars.api.sys.entity.UserEntity>
     */
    List<UserEntity> selectListWithRole(@Param("u") UserEntity userEntity, @Param("r") RoleEntity roleEntity);
}
