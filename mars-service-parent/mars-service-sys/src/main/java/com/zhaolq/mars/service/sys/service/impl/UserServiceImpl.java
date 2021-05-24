package com.zhaolq.mars.service.sys.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhaolq.mars.service.sys.entity.MenuEntity;
import com.zhaolq.mars.service.sys.entity.RoleEntity;
import com.zhaolq.mars.service.sys.entity.UserEntity;
import com.zhaolq.mars.service.sys.mapper.UserMapper;
import com.zhaolq.mars.service.sys.service.IUserService;
import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 用户管理 服务实现类
 * </p>
 *
 * @author zhaolq
 * @since 2020-10-29
 */
@Service
@AllArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements IUserService {

    UserMapper userMapper;

    @Override
    public UserEntity getWithRole(UserEntity userEntity, RoleEntity roleEntity) {
        return userMapper.getWithRole(userEntity, roleEntity);
    }

    @Override
    public List<UserEntity> getWithRoleList(UserEntity userEntity, RoleEntity roleEntity) {
        return userMapper.getWithRoleList(userEntity, roleEntity);
    }

    @Override
    public IPage<UserEntity> getWithRolePage(IPage<UserEntity> page, UserEntity userEntity, RoleEntity roleEntity) {
        return userMapper.getWithRolePage(page, userEntity, roleEntity);
    }

    @Override
    public Set<MenuEntity> getAuthorityMenu(UserEntity userEntity) {
        return userMapper.getAuthorityMenu(userEntity);
    }

}
