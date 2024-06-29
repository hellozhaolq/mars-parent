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
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.ManagedHttpClientConnectionFactory;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.io.HttpClientConnectionManager;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactoryBuilder;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.http.io.SocketConfig;
import org.apache.hc.core5.http.ssl.TLS;
import org.apache.hc.core5.pool.PoolConcurrencyPolicy;
import org.apache.hc.core5.pool.PoolReusePolicy;
import org.apache.hc.core5.ssl.SSLContexts;
import org.apache.hc.core5.util.TimeValue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

/**
 * HttpClient配置
 * <pre>
 * 高并发场景下的httpClient优化方案：
 *   1、单例的client
 *   2、缓存的保活连接(PoolingHttpClientConnectionManager连接池)
 *   3、定时关闭连接，放回池中，还是真的关闭了呢？如何验证
 *   4、更好的处理返回结果。推荐定义一个ResponseHandler，不再自己catch异常和关闭流。由源码得，执行带有ResponseHandler参数execute方法，最终会调用EntityUtils.consume()方法关闭资源。不调用close，不归还连接。
 * </pre>
 * <pre>
 * http请求工具
 *   Java：   HttpURLConnection 同步阻塞
 *   spring： RestTemplate 同步阻塞（默认Http客户端类型是 JDK原生的URLConnection）、WebClient 异步非阻塞式客户端
 *   apache： HttpComponents 官网：HttpCore 支持两种 I/O 模型：基于经典 Java I/O 的阻塞 I/O 模型和基于 Java NIO 的非阻塞、事件驱动 I/O 模型。
 * </pre>
 *
 * @Author zhaolq
 * @Date 2023/5/30 17:58:06
 */
@Slf4j
@Configuration
public class HttpClientConfig {
    @Resource
    private HttpClientProp httpClientProp;

    /**
     * 池化HttpClient连接管理器，并设置最大连接数、并发连接数
     */
    @Bean(name = "httpClientConnectionManager")
    public HttpClientConnectionManager httpClientConnectionManager(
            @Qualifier("socketConfig") SocketConfig socketConfig,
            @Qualifier("connectionConfig") ConnectionConfig connectionConfig,
            @Qualifier("tlsConfig") TlsConfig tlsConfig) {
        PoolingHttpClientConnectionManager connectionManager = PoolingHttpClientConnectionManagerBuilder.create()
                .useSystemProperties()
                .setMaxConnTotal(httpClientProp.getDefaultMaxTotalConnections())
                .setMaxConnPerRoute(httpClientProp.getDefaultMaxConnectionsPerRoute())
                .setSSLSocketFactory(SSLConnectionSocketFactoryBuilder.create()
                        .setSslContext(SSLContexts.createSystemDefault())
                        .build())
                .setPoolConcurrencyPolicy(PoolConcurrencyPolicy.STRICT)
                .setConnPoolPolicy(PoolReusePolicy.LIFO)
                .setDefaultSocketConfig(socketConfig) // setSocketConfigResolver 可以为每个路由设置套接字配置解析器
                .setDefaultConnectionConfig(connectionConfig) // setConnectionConfigResolver 可以为每个路由设置连接配置解析器
                .setDefaultTlsConfig(tlsConfig)
                .setSchemePortResolver(DefaultSchemePortResolver.INSTANCE) // 默认协议端口解析器
                .setDnsResolver(SystemDefaultDnsResolver.INSTANCE) // 系统默认dns解析器
                .setConnectionFactory(ManagedHttpClientConnectionFactory.INSTANCE) // 托管http客户端连接工厂
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
    @Bean(name = "httpClientBuilder")
    public HttpClientBuilder httpClientBuilder(
            @Qualifier("httpClientConnectionManager") HttpClientConnectionManager httpClientConnectionManager,
            @Qualifier("requestConfig") RequestConfig requestConfig) {
        // 建造者模式，HttpClientBuilder中的构造方法被protected修饰
        HttpClientBuilder httpClientBuilder = HttpClients.custom()
                .useSystemProperties()
                .setConnectionManager(httpClientConnectionManager) // 设置连接池
                .setDefaultRequestConfig(requestConfig) // 请求配置
                .setDefaultCookieStore(new BasicCookieStore())
                .setDefaultCredentialsProvider(null)
                .setDefaultHeaders(Collections.emptyList())
                .setProxy(new HttpHost("http", "127.0.0.1", 1081))
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
        return httpClientBuilder;
    }

    /**
     * 获取httpClient
     *
     * @param httpClientBuilder httpClientBuilder
     */
    @Bean(name = "closeableHttpClient")
    public CloseableHttpClient closeableHttpClient(@Qualifier("httpClientBuilder") HttpClientBuilder httpClientBuilder) {
        // 返回的实际类型为 InternalHttpClient
        return httpClientBuilder.build();
    }

}
