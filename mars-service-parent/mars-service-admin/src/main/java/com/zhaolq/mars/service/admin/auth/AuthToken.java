package com.zhaolq.mars.service.admin.auth;

import java.util.Map;

/**
 * 鉴权token相关
 *
 * @author zhaolq
 * @date 2023/4/13 17:39:45
 * @since 1.0.0
 */
public class AuthToken {
    private static final long DEFAULT_EXPIRED_TIME_INTERVAL_SECOND = 1 * 60 * 1000;
    private String token;
    private long createTime;
    private long expiredTimeInterval = DEFAULT_EXPIRED_TIME_INTERVAL_SECOND;

    public AuthToken(String token, long createTime) {
        this.token = token;
        this.createTime = createTime;
    }

    public AuthToken(String token, long createTime, long expiredTimeInterval) {
        this.token = token;
        this.createTime = createTime;
        this.expiredTimeInterval = expiredTimeInterval;
    }

    public static AuthToken generate(String originalUrl, long createTime, Map<String, String> params) {
        return null;
    }

    public String getToken() {
        return token;
    }

    public boolean isExpired() {
        return false;
    }

    public boolean match(AuthToken authToken) {
        return false;
    }
}
