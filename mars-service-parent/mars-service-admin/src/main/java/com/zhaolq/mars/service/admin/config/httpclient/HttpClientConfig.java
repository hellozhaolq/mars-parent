package com.zhaolq.mars.service.admin.config.httpclient;

import java.util.Collections;

import org.apache.hc.client5.http.SystemDefaultDnsResolver;
import org.apache.hc.client5.http.config.ConnectionConfig;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.config.TlsConfig;
import org.apache.hc.client5.http.cookie.BasicCookieStore;
import org.apache.hc.client5.http.cookie.StandardCookieSpec;
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
import org.apache.hc.core5.util.Timeout;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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

    @Bean(name = "socketConfig")
    public SocketConfig socketConfig() {
        return SocketConfig.custom()
                .setTcpNoDelay(true)
                .setSoLinger(TimeValue.NEG_ONE_SECOND)
                .setSoTimeout(Timeout.ofSeconds(defaultSocketTimeout))
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
                .setConnectTimeout(Timeout.ofSeconds(defaultConnectTimeout))
                .setTimeToLive(TimeValue.NEG_ONE_MILLISECOND) // Time to Live (TTL)
                .setValidateAfterInactivity(TimeValue.ofSeconds(validateAfterInactivity))
                .build();
    }

    @Bean(name = "tlsConfig")
    public TlsConfig tlsConfig() {
        return TlsConfig.custom()

                .build();
    }

    @Bean(name = "requestConfig")
    public RequestConfig requestConfig() {
        // Builder是RequestConfig的一个内部类，通过RequestConfig.custom()获取RequestConfig.Builder对象，进而设置请求配置项
        return RequestConfig.custom()
                .setConnectionRequestTimeout(Timeout.ofSeconds(defaultConnectionRequestTimeout))
                .setConnectionKeepAlive(TimeValue.ofSeconds(defaultConnKeepAlive)) // 具体由Keep-Alive策略决定
                .setCookieSpec(StandardCookieSpec.STRICT)
                .build();
    }

    /**
     * 池化HttpClient连接管理器，并设置最大连接数、并发连接数
     */
    @Bean(name = "httpClientConnectionManager")
    public HttpClientConnectionManager httpClientConnectionManager(
            @Qualifier("socketConfig") SocketConfig socketConfig,
            @Qualifier("connectionConfig") ConnectionConfig connectionConfig,
            @Qualifier("tlsConfig") TlsConfig tlsConfig) {
        PoolingHttpClientConnectionManager connPoolManager = PoolingHttpClientConnectionManagerBuilder.create()
                .useSystemProperties()
                .setMaxConnTotal(defaultMaxTotalConnections)
                .setMaxConnPerRoute(defaultMaxConnectionsPerRoute)
                .setSSLSocketFactory(SSLConnectionSocketFactoryBuilder.create()
                        .setSslContext(SSLContexts.createSystemDefault())
                        .setTlsVersions(TLS.V_1_3)
                        .build())
                .setPoolConcurrencyPolicy(PoolConcurrencyPolicy.STRICT)
                .setConnPoolPolicy(PoolReusePolicy.LIFO)
                .setSchemePortResolver(DefaultSchemePortResolver.INSTANCE) // 默认协议端口解析器
                .setDnsResolver(SystemDefaultDnsResolver.INSTANCE) // 系统默认dns解析器
                .setConnectionFactory(ManagedHttpClientConnectionFactory.INSTANCE) // 托管http客户端连接工厂
                .setDefaultSocketConfig(socketConfig) // setSocketConfigResolver 可以为每个路由设置套接字配置解析器
                .setDefaultConnectionConfig(connectionConfig) // setConnectionConfigResolver 可以为每个路由设置连接配置解析器
                .setDefaultTlsConfig(tlsConfig)
                .build(); // setTlsConfigResolver 可以为每个路由设置TLS配置解析器
        return connPoolManager;
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
                .setProxy(new HttpHost("SOCKS5", "127.0.0.1", 1080))
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
        // build()方法很长，做了很多配置判断。返回的实际类型为 InternalHttpClient
        return httpClientBuilder.build();
    }

}
