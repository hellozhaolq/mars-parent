package com.zhaolq.mars.service.base.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhaolq.mars.api.sys.entity.PoliticalStatusEntity;
import com.zhaolq.mars.service.base.mapper.base.PoliticalStatusMapper;
import com.zhaolq.mars.service.base.service.IPoliticalStatusService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 政治面貌 服务实现类
 * </p>
 *
 * @author zhaolq
 * @date 2020-10-29
 */
@Service
public class PoliticalStatusServiceImpl extends ServiceImpl<PoliticalStatusMapper, PoliticalStatusEntity> implements IPoliticalStatusService {

}
