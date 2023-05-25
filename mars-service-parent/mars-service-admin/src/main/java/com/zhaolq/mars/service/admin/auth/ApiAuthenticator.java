package com.zhaolq.mars.service.admin.auth;

/**
 * API鉴权
 *
 * @author zhaolq
 * @date 2023/4/13 17:36:50
 * @since 1.0.0
 */
public interface ApiAuthenticator {
    void auth(String url);
    void auth(ApiRequest apiRequest);
}
