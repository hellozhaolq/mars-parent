package com.zhaolq.services.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhaolq.services.sys.entity.MediaFileEntity;
import com.zhaolq.services.sys.mapper.MediaFileMapper;
import com.zhaolq.services.sys.service.IMediaFileService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 图片、文件、音乐等媒体文件 服务实现类
 * </p>
 *
 * @author zhaolq
 * @since 2020-10-29
 */
@Service
public class MediaFileServiceImpl extends ServiceImpl<MediaFileMapper, MediaFileEntity> implements IMediaFileService {

}
