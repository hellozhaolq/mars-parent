package com.zhaolq.mars.service.admin.config.httpclient;

import org.apache.hc.client5.http.config.ConnectionConfig;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.config.TlsConfig;
import org.apache.hc.client5.http.cookie.StandardCookieSpec;
import org.apache.hc.core5.http.io.SocketConfig;
import org.apache.hc.core5.http2.HttpVersionPolicy;
import org.apache.hc.core5.util.TimeValue;
import org.apache.hc.core5.util.Timeout;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.Resource;

/**
 * 初始化通用配置
 *
 * @Author zhaolq
 * @Date 2024/6/29 16:27
 */
@Configuration
public class HttpClientCommonConfig {
    @Resource
    private HttpClientProp httpClientProp;

    @Bean(name = "socketConfig")
    public SocketConfig socketConfig() {
        return SocketConfig.custom()
                .setTcpNoDelay(true)
                .setSoLinger(TimeValue.NEG_ONE_SECOND)
                .setSoTimeout(Timeout.ofSeconds(httpClientProp.getDefaultSocketTimeout()))
                .build();
    }

    @Bean(name = "connectionConfig")
    public ConnectionConfig connectionConfig() {
        /**
         * TimeToLive为连接存活的最大时间，每次获取的连接只要存活超时，即使服务器仍保持连接，客户端也会断开重新获取连接。客户端指的是httpclient
         * 推荐TimeToLive保持默认值-1就好，这样优先使用服务器返回的KeepAlive，只要在KeepAlive时间范围内，client就不会主动关闭。
         */
        return ConnectionConfig.custom()
                .setSocketTimeout(Timeout.ofMinutes(1))
                .setConnectTimeout(Timeout.ofSeconds(httpClientProp.getDefaultConnectTimeout()))
                .setTimeToLive(TimeValue.NEG_ONE_MILLISECOND) // Time to Live (TTL)
                .setValidateAfterInactivity(TimeValue.ofSeconds(httpClientProp.getValidateAfterInactivity()))
                .build();
    }

    @Bean(name = "tlsConfig")
    public TlsConfig tlsConfig() {
        return TlsConfig.custom()
                .setVersionPolicy(HttpVersionPolicy.NEGOTIATE) // HTTP协议版本策略
                .setHandshakeTimeout(Timeout.ofMinutes(1))
                .build();
    }

    @Bean(name = "requestConfig")
    public RequestConfig requestConfig() {
        // Builder是RequestConfig的一个内部类，通过RequestConfig.custom()获取RequestConfig.Builder对象，进而设置请求配置项
        return RequestConfig.custom()
                .setConnectionRequestTimeout(Timeout.ofSeconds(httpClientProp.getDefaultConnectionRequestTimeout()))
                .setConnectionKeepAlive(TimeValue.ofSeconds(httpClientProp.getDefaultConnKeepAlive())) // 具体由Keep-Alive策略决定
                .setCookieSpec(StandardCookieSpec.STRICT)
                .build();
    }
}
