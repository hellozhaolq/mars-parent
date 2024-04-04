package com.zhaolq.mars.service.admin.auth.storage;

/**
 * 从存储中取出AppID和对应的密码
 *
 * @Author zhaolq
 * @Date 2023/4/13 17:52:10
 * @Since 1.0.0
 */
public interface CredentialStorage {
    String getPasswordByAppId(String appId);
}
