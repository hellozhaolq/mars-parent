package com.zhaolq.mars.service.admin.config.httpclient;

import java.util.Collections;

import org.apache.hc.client5.http.SystemDefaultDnsResolver;
import org.apache.hc.client5.http.config.ConnectionConfig;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.config.TlsConfig;
import org.apache.hc.client5.http.cookie.BasicCookieStore;
import org.apache.hc.client5.http.impl.DefaultClientConnectionReuseStrategy;
import org.apache.hc.client5.http.impl.DefaultConnectionKeepAliveStrategy;
import org.apache.hc.client5.http.impl.DefaultHttpRequestRetryStrategy;
import org.apache.hc.client5.http.impl.DefaultSchemePortResolver;
import org.apache.hc.client5.http.impl.async.CloseableHttpAsyncClient;
import org.apache.hc.client5.http.impl.async.HttpAsyncClientBuilder;
import org.apache.hc.client5.http.impl.async.HttpAsyncClients;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.nio.PoolingAsyncClientConnectionManager;
import org.apache.hc.client5.http.impl.nio.PoolingAsyncClientConnectionManagerBuilder;
import org.apache.hc.client5.http.nio.AsyncClientConnectionManager;
import org.apache.hc.client5.http.ssl.ClientTlsStrategyBuilder;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.pool.PoolConcurrencyPolicy;
import org.apache.hc.core5.pool.PoolReusePolicy;
import org.apache.hc.core5.reactor.IOReactorConfig;
import org.apache.hc.core5.ssl.SSLContexts;
import org.apache.hc.core5.util.TimeValue;
import org.apache.hc.core5.util.Timeout;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

/**
 * HttpClient异步配置
 *
 * @Author zhaolq
 * @Date 2024/6/29 15:54
 */
@Slf4j
@Configuration
public class HttpAsyncClientConfig {
    @Resource
    private HttpClientProp httpClientProp;

    /**
     * 池化HttpClient连接管理器，并设置最大连接数、并发连接数
     */
    @Bean(name = "asyncClientConnectionManager")
    public AsyncClientConnectionManager asyncClientConnectionManager(
            @Qualifier("connectionConfig") ConnectionConfig connectionConfig,
            @Qualifier("tlsConfig") TlsConfig tlsConfig) {
        PoolingAsyncClientConnectionManager connectionManager = PoolingAsyncClientConnectionManagerBuilder.create()
                .useSystemProperties()
                .setTlsStrategy(ClientTlsStrategyBuilder.create()
                        .setSslContext(SSLContexts.createSystemDefault())
                        .build())
                .setMaxConnTotal(httpClientProp.getDefaultMaxTotalConnections())
                .setMaxConnPerRoute(httpClientProp.getDefaultMaxConnectionsPerRoute())
                .setPoolConcurrencyPolicy(PoolConcurrencyPolicy.STRICT)
                .setConnPoolPolicy(PoolReusePolicy.LIFO)
                .setDefaultConnectionConfig(connectionConfig) // setConnectionConfigResolver 可以为每个路由设置连接配置解析器
                .setDefaultTlsConfig(tlsConfig)
                .setSchemePortResolver(DefaultSchemePortResolver.INSTANCE) // 默认协议端口解析器
                .setDnsResolver(SystemDefaultDnsResolver.INSTANCE) // 系统默认dns解析器
                .build(); // setTlsConfigResolver 可以为每个路由设置TLS配置解析器
        return connectionManager;
    }

    /**
     * http客户端生成器对象
     * <p>
     * {@link HttpClients} 使用了工厂模式；
     * {@link HttpClientBuilder} 使用了建造者模式
     *
     * @param httpClientConnectionManager httpClientConnectionManager
     * @param requestConfig               requestConfig
     * @return org.apache.http.impl.client.HttpClientBuilder
     */
    @Bean(name = "httpAsyncClientBuilder")
    public HttpAsyncClientBuilder httpAsyncClientBuilder(
            @Qualifier("asyncClientConnectionManager") AsyncClientConnectionManager asyncClientConnectionManager,
            @Qualifier("requestConfig") RequestConfig requestConfig) {
        // 建造者模式，HttpClientBuilder中的构造方法被protected修饰
        HttpAsyncClientBuilder httpAsyncClientBuilder = HttpAsyncClients.custom()
                .useSystemProperties()
                .setConnectionManager(asyncClientConnectionManager) // 设置连接池
                .setIOReactorConfig(IOReactorConfig.custom()
                        .setSoTimeout(Timeout.ofMinutes(1))
                        .build())
                .setDefaultRequestConfig(requestConfig) // 请求配置
                .setDefaultCookieStore(new BasicCookieStore())
                .setDefaultCredentialsProvider(null)
                .setDefaultHeaders(Collections.emptyList())
                .setProxy(new HttpHost("http", "127.0.0.1", 1081)) // 不支持socks5协议
                .setConnectionReuseStrategy(DefaultClientConnectionReuseStrategy.INSTANCE) // 默认连接重用策略
                // 设置Keep-Alive策略为DefaultConnectionKeepAliveStrategy，读取response中keep-alive的timeout参数，若是没有则为 requestConfig.getConnectionKeepAlive()。
                .setKeepAliveStrategy(DefaultConnectionKeepAliveStrategy.INSTANCE)
                // 请看build()方法源码，当userAgent为null时会初始化，然后创建RequestUserAgent并添加到HttpProcessorBuilder
                .setUserAgent(null) // 无需再调用addRequestInterceptorLast(new RequestUserAgent(null))，否则会重复处理
                .setRetryStrategy(new DefaultHttpRequestRetryStrategy()) // http幂等请求 https://developer.mozilla.org/zh-CN/docs/Glossary/Idempotent
                .disableAutomaticRetries() // 禁止重试策略。在高并发场景下建议关闭。
                // 使该 HttpClient 实例使用后台线程（ThreadName见源码）主动从连接池中驱逐过期的连接。默认不开启。如果 HttpClient 实例配置为使用共享连接管理器，则此方法无效。
                .evictExpiredConnections()
                // 使该 HttpClient 实例使用后台线程（ThreadName见源码）主动从连接池中驱逐空闲连接。默认不开启。如果 HttpClient 实例配置为使用共享连接管理器，则此方法无效。
                .evictIdleConnections(TimeValue.ofSeconds(180));
        return httpAsyncClientBuilder;
    }

    /**
     * 获取httpClient
     *
     * @param httpClientBuilder httpClientBuilder
     */
    @Bean(name = "closeableHttpAsyncClient")
    public CloseableHttpAsyncClient closeableHttpAsyncClient(@Qualifier("httpAsyncClientBuilder") HttpAsyncClientBuilder httpAsyncClientBuilder) {
        // 返回的实际类型为 InternalHttpAsyncClient
        CloseableHttpAsyncClient closeableHttpAsyncClient = httpAsyncClientBuilder.build();
        closeableHttpAsyncClient.start();
        return closeableHttpAsyncClient;
    }

}
