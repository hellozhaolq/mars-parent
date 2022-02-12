package com.zhaolq.mars.service.sys.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.zhaolq.mars.api.sys.entity.MenuEntity;
import com.zhaolq.mars.api.sys.entity.RoleEntity;
import com.zhaolq.mars.api.sys.entity.UserEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 用户管理 Mapper 接口
 * </p>
 *
 * @author zhaolq
 * @date 2020-10-29
 */
@Transactional(rollbackFor = Exception.class)
public interface UserMapper extends BaseMapper<UserEntity> {

    /**
     * 使用 Wrapper 自定义SQL
     *
     * @param wrapper
     * @return java.util.List<com.zhaolq.mars.api.sys.entity.UserEntity>
     */
    // @Select("select * from tab_earth_user ${ew.customSqlSegment}")
    List<UserEntity> selectByWrapperCustom(@Param(Constants.WRAPPER) Wrapper<UserEntity> wrapper);

    /**
     * 使用 Wrapper 自定义分页
     *
     * @param page
     * @param wrapper
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.zhaolq.mars.api.sys.entity.UserEntity>
     */
    IPage<UserEntity> selectPageByWrapperCustom(IPage<UserEntity> page, @Param(Constants.WRAPPER) Wrapper<UserEntity> wrapper);

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

    /**
     * 分页查询，携带角色列表，连表查询，多个参数
     *
     * @param page
     * @param userEntity
     * @param roleEntity
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.zhaolq.mars.api.sys.entity.UserEntity>
     */
    @Deprecated
    IPage<UserEntity> selectPageWithRole(IPage<UserEntity> page, @Param("u") UserEntity userEntity, @Param("r") RoleEntity roleEntity);

    /**
     * 分页查询，携带角色列表，关联的嵌套Select查询(N+1查询问题)
     *
     * @param page
     * @param userEntity
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.zhaolq.mars.api.sys.entity.UserEntity>
     */
    IPage<UserEntity> selectPageWithRoleNestedSelectTest(IPage<UserEntity> page, @Param("u") UserEntity userEntity);

    /**
     * 获取权限下菜单
     *
     * @param userEntity
     * @return java.util.Set<com.zhaolq.mars.api.sys.entity.MenuEntity>
     */
    List<MenuEntity> selectAuthorityMenu(@Param("u") UserEntity userEntity);

}
