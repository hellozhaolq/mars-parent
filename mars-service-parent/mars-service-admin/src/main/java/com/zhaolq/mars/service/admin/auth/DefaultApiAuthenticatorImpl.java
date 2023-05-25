package com.zhaolq.mars.service.admin.auth;


import com.zhaolq.mars.service.admin.auth.storage.CredentialStorage;
import com.zhaolq.mars.service.admin.auth.storage.MysqlCredentialStorage;

/**
 * API鉴权的默认实现
 *
 * @author zhaolq
 * @date 2023/4/13 17:37:24
 * @since 1.0.0
 */
public class DefaultApiAuthenticatorImpl implements ApiAuthenticator {
    private CredentialStorage credentialStorage;

    public DefaultApiAuthenticatorImpl() {
        this.credentialStorage = new MysqlCredentialStorage();
    }

    public DefaultApiAuthenticatorImpl(CredentialStorage credentialStorage) {
        this.credentialStorage = credentialStorage;
    }

    @Override
    public void auth(String url) {
        ApiRequest apiRequest = ApiRequest.buildFromUrl(url);
        auth(apiRequest);
    }

    @Override
    public void auth(ApiRequest apiRequest) {
        String originalUrl = apiRequest.getOriginalUrl();
        String token = apiRequest.getToken();
        String appId = apiRequest.getAppId();
        long timestamp = apiRequest.getTimestamp();

        AuthToken clientAuthToken = new AuthToken(token, timestamp);
        if (clientAuthToken.isExpired()) {
            throw new RuntimeException("Token is expired.");
        }

        // String password = credentialStorage.getPasswordByAppId(appId);
        // AuthToken serverAuthToken = AuthToken.generate(originalUrl, timestamp, appId, password);
        // if (!serverAuthToken.match(clientAuthToken)) {
        //     throw new RuntimeException("Token verfication failed.");
        // }
    }
}
