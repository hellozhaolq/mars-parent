package com.zhaolq.mars.service.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhaolq.mars.service.sys.entity.MenuEntity;
import com.zhaolq.mars.service.sys.mapper.MenuMapper;
import com.zhaolq.mars.service.sys.service.IMenuService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 菜单管理 服务实现类
 * </p>
 *
 * @author zhaolq
 * @since 2020-10-29
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, MenuEntity> implements IMenuService {

}
