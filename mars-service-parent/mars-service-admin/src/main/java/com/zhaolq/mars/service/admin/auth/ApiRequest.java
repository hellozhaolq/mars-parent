package com.zhaolq.mars.service.admin.auth;

/**
 * 封装API请求的相关属性
 *
 * @Author zhaolq
 * @Date 2023/4/13 17:38:59
 */
public class ApiRequest {
    private String originalUrl;
    private String token;
    private String appId;
    private long timestamp;

    public ApiRequest(String originalUrl, String token, String appId, long timestamp) {
        this.originalUrl = originalUrl;
        this.token = token;
        this.appId = appId;
        this.timestamp = timestamp;
    }

    public static ApiRequest buildFromUrl(String url) {
        return null;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public String getToken() {
        return token;
    }

    public String getAppId() {
        return appId;
    }

    public long getTimestamp() {
        return timestamp;
    }

}
