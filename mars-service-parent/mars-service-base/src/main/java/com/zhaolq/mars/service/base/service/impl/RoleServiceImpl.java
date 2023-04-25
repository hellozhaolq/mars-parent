package com.zhaolq.mars.service.base.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhaolq.mars.api.sys.entity.RoleEntity;
import com.zhaolq.mars.service.base.mapper.base.RoleMapper;
import com.zhaolq.mars.service.base.service.IRoleService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色管理 服务实现类
 * </p>
 *
 * @author zhaolq
 * @date 2020-10-29
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, RoleEntity> implements IRoleService {

}
