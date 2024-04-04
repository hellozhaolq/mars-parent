package com.zhaolq.mars.service.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhaolq.mars.api.admin.entity.MediaFileEntity;
import com.zhaolq.mars.service.admin.dao.base.MediaFileMapper;
import com.zhaolq.mars.service.admin.service.IMediaFileService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 图片、文件、音乐等媒体文件 服务实现类
 * </p>
 *
 * @Author zhaolq
 * @Date 2020-10-29
 */
@Service
public class MediaFileServiceImpl extends ServiceImpl<MediaFileMapper, MediaFileEntity> implements IMediaFileService {

}
