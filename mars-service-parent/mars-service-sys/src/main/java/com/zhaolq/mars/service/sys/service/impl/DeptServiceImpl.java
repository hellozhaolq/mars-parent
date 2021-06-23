package com.zhaolq.mars.service.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhaolq.mars.api.sys.entity.DeptEntity;
import com.zhaolq.mars.service.sys.mapper.DeptMapper;
import com.zhaolq.mars.service.sys.service.IDeptService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 机构管理 服务实现类
 * </p>
 *
 * @author zhaolq
 * @since 2020-10-29
 */
@Service
public class DeptServiceImpl extends ServiceImpl<DeptMapper, DeptEntity> implements IDeptService {

}
