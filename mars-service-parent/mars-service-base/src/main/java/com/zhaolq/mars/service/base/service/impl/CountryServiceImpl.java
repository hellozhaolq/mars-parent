package com.zhaolq.mars.service.base.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhaolq.mars.api.sys.entity.CountryEntity;
import com.zhaolq.mars.service.base.service.ICountryService;
import com.zhaolq.mars.service.base.mapper.base.CountryMapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 国家 ISO 3166-1 服务实现类
 * </p>
 *
 * @author zhaolq
 * @date 2020-10-29
 */
@Service
public class CountryServiceImpl extends ServiceImpl<CountryMapper, CountryEntity> implements ICountryService {

}
