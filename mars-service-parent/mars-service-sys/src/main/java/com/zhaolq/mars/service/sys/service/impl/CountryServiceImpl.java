package com.zhaolq.mars.service.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhaolq.mars.api.sys.entity.CountryEntity;
import com.zhaolq.mars.service.sys.service.ICountryService;
import com.zhaolq.mars.service.sys.mapper.CountryMapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 国家 ISO 3166-1 服务实现类
 * </p>
 *
 * @author zhaolq
 * @since 2020-10-29
 */
@Service
public class CountryServiceImpl extends ServiceImpl<CountryMapper, CountryEntity> implements ICountryService {

}
