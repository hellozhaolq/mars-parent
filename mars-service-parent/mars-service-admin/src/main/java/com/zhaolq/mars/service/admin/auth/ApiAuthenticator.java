package com.zhaolq.mars.service.admin.auth;

/**
 * API鉴权
 *
 * @Author zhaolq
 * @Date 2023/4/13 17:36:50
 */
public interface ApiAuthenticator {
    void auth(String url);

    void auth(ApiRequest apiRequest);
}
