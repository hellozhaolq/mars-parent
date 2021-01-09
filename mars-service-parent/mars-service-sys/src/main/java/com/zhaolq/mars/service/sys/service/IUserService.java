package com.zhaolq.mars.service.sys.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhaolq.mars.service.sys.entity.UserEntity;

import java.io.File;

/**
 * <p>
 * 用户管理 服务类
 * </p>
 *
 * @author zhaolq
 * @since 2020-10-29
 */
public interface IUserService extends IService<UserEntity> {

    /**
     * 生成用户信息excel文件
     *
     * @param page
     * @return java.io.File
     */
    File createExcelFile(Page<UserEntity> page);
}
