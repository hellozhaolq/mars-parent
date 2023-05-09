package com.zhaolq.mars.service.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhaolq.mars.api.admin.entity.DeptEntity;
import com.zhaolq.mars.service.admin.dao.base.DeptMapper;
import com.zhaolq.mars.service.admin.service.IDeptService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 机构管理 服务实现类
 * </p>
 *
 * @author zhaolq
 * @date 2020-10-29
 */
@Service
public class DeptServiceImpl extends ServiceImpl<DeptMapper, DeptEntity> implements IDeptService {

}
