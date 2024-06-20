package com.zhaolq.mars.service.admin.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhaolq.mars.service.admin.dao.base.UserMapper;
import com.zhaolq.mars.service.admin.entity.RoleEntity;
import com.zhaolq.mars.service.admin.entity.UserEntity;
import com.zhaolq.mars.service.admin.service.IUserService;

import io.mybatis.service.AbstractService;
import lombok.AllArgsConstructor;

/**
 * <p>
 * 用户管理 服务实现类
 * </p>
 *
 * @Author zhaolq
 * @Date 2020-10-29
 */
@Service
@AllArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl extends AbstractService<UserEntity, String, UserMapper> implements IUserService {
    private UserMapper userMapper;

    @Override
    public UserEntity getWithRole(UserEntity userEntity, RoleEntity roleEntity) {
        return userMapper.selectWithRole(userEntity, roleEntity);
    }

    @Override
    public UserEntity getWithRoleNestedSelectTest(UserEntity userEntity) {
        return userMapper.selectWithRoleNestedSelectTest(userEntity);
    }

    @Override
    public List<UserEntity> listWithRole(UserEntity userEntity, RoleEntity roleEntity) {
        return userMapper.selectListWithRole(userEntity, roleEntity);
    }
}
