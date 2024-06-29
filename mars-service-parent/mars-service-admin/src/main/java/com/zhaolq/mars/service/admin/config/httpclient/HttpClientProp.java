package com.zhaolq.mars.service.admin.config.httpclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

/**
 * 基础属性配置
 *
 * @Author zhaolq
 * @Date 2024/6/29 16:04
 */
@Data
@Configuration
public class HttpClientProp {
    /**
     * 连接池大小，最大连接数
     */
    @Value("${http.default-max-total-connections:25}")
    private int defaultMaxTotalConnections;

    /**
     * 每个Route(路由)的最大连接数(并发数)，根据连接到的host对MaxTotal的一个细分。
     * 比如：MaxTotal=400，DefaultMaxPerRoute=200。
     * 当只连接到 https://www.baidu.com 时，到这个host的并发最多只有200，而不是400。
     * 当连接到 https://www.baidu.com 和 https://www.google.com 时，到每个host的并发最多只有200，加起来是400（但不能超过400）。
     */
    @Value("${http.default-max-connections-per-route:5}")
    private int defaultMaxConnectionsPerRoute;

    /**
     * 数据传输的超时时间，socket等待数据的超时时间，响应超时时间（以秒为单位）。
     * 定义套接字超时 （SO_TIMEOUT），它是等待数据的超时，或者换句话说，两个连续数据包之间的最长非活动时间。避免阻塞。
     * <p>
     * 测试本地一个url，让线程sleep一段时间，来模拟返回response超时。
     */
    @Value("${http.default-socket-timeout:180}")
    private long defaultSocketTimeout;

    /**
     * 网络连接(与服务器连接)超时时间，发起请求前创建socket连接的超时时间（以毫秒为单位）。
     * <p>
     * 测试的时候，将url改为一个不存在的url：http://test.com，超时时间过后，则会抛出
     * {@link org.apache.hc.client5.http.ConnectTimeoutException}。
     */
    @Value("${http.default_connect_timeout:180}")
    private long defaultConnectTimeout;

    /**
     * 验证连接是否活动的周期（以秒为单位）。在该周期之后，会校验连接是否可用（调用socket读取）。负值将禁用连接验证。
     * 此检查有助于检测已过时（TCP半关闭）的连接，而在池中依然保持非活动状态。
     * <p>
     * 更好的方式是，使用后台线程主动从连接池中驱逐空闲连接。httpClientBuilder.evictIdleConnections(3, TimeUnit.SECONDS);
     */
    @Value("${http.validate-after-inactivity:60}")
    private long validateAfterInactivity;

    /**
     * 从HttpClient连接池（HttpClient连接管理器）中获取连接的超时时间（以秒为单位）。
     */
    @Value("${http.default-connection-request-timeout:180}")
    private long defaultConnectionRequestTimeout;

    @Value("${http.default-conn-keep-alive:180}")
    private long defaultConnKeepAlive;

    private HttpClientProp() {
    }

    public static HttpClientProp.Builder custom() {
        return new HttpClientProp.Builder();
    }

    public static class Builder {
        public HttpClientProp build() {
            return new HttpClientProp();
        }
    }
}
