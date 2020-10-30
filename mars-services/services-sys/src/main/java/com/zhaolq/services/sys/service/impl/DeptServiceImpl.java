package com.zhaolq.services.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhaolq.services.sys.entity.DeptEntity;
import com.zhaolq.services.sys.mapper.DeptMapper;
import com.zhaolq.services.sys.service.IDeptService;
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
