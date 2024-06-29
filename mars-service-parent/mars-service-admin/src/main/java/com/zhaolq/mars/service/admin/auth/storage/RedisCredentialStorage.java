package com.zhaolq.mars.service.admin.auth.storage;

/**
 * 从Redis中取出AppID和对应的密码
 *
 * @Author zhaolq
 * @Date 2023/4/14 9:10:45
 */
public class RedisCredentialStorage implements CredentialStorage {
    @Override
    public String getPasswordByAppId(String appId) {
        return "Redis";
    }
}
