package com.zhaolq.mars.service.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhaolq.mars.api.admin.entity.MenuEntity;
import com.zhaolq.mars.service.admin.dao.base.MenuMapper;
import com.zhaolq.mars.service.admin.service.IMenuService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 菜单管理 服务实现类
 * </p>
 *
 * @Author zhaolq
 * @Date 2020-10-29
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, MenuEntity> implements IMenuService {

}
