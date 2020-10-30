package com.zhaolq.services.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhaolq.services.sys.entity.UserRoleEntity;
import com.zhaolq.services.sys.mapper.UserRoleMapper;
import com.zhaolq.services.sys.service.IUserRoleService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户角色 服务实现类
 * </p>
 *
 * @author zhaolq
 * @since 2020-10-29
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRoleEntity> implements IUserRoleService {

}
