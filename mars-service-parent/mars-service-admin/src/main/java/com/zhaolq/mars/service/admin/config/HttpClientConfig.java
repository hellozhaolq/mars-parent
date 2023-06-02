package com.zhaolq.mars.service.admin.config;

import java.util.concurrent.TimeUnit;

import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.StandardHttpRequestRetryHandler;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.Args;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

/**
 * Apache Http 组件配置
 * <pre>
 * 高并发场景下的httpClient优化方案：
 *   1、单例的client
 *   2、缓存的保活连接(PoolingHttpClientConnectionManager连接池)
 *   3、定时关闭连接，放回池中，还是真的关闭了呢？如何验证
 *   4、更好的处理返回结果。推荐定义一个ResponseHandler，不再自己catch异常和关闭流。由源码得，执行带有ResponseHandler参数execute方法，最终会调用EntityUtils.consume()方法关闭资源。不调用close，不归还连接。
 * </pre>
 *
 * @author zhaolq
 * @date 2023/5/30 17:58:06
 * @since 1.0.0
 */
@Slf4j
@Configuration
public class HttpClientConfig {

    /**
     * 连接池大小，最大连接数
     */
    @Value("${http.maxTotal:500}")
    private int maxTotal;

    /**
     * 每个Route(路由)的最大连接数(并发数)，根据连接到的host对MaxTotal的一个细分。
     * 比如：MaxTotal=400，DefaultMaxPerRoute=200。
     * 当只连接到 https://www.baidu.com 时，到这个host的并发最多只有200，而不是400。
     * 当连接到 https://www.baidu.com 和 https://www.google.com 时，到每个host的并发最多只有200，加起来是400（但不能超过400）。
     */
    @Value("${http.defaultMaxPerRoute:50}")
    private int defaultMaxPerRoute;

    /**
     * 验证连接是否活动的周期（以毫秒为单位）。在该周期之后，会校验连接是否可用（调用socket读取）。负值将禁用连接验证。
     * 此检查有助于检测已过时（TCP半关闭）的连接，而在池中依然保持非活动状态。
     * <p>
     * 更好的方式是，使用后台线程主动从连接池中驱逐空闲连接。httpClientBuilder.evictIdleConnections(3, TimeUnit.SECONDS);
     */
    @Value("${http.validateAfterInactivity:2000}")
    private int validateAfterInactivity;

    /**
     * 从HttpClient连接池（HttpClient连接管理器）中获取连接的超时时间（以毫秒为单位）。
     * <p>
     * 并发请求的连接数超过了DefaultMaxPerRoute设置。并且在ConnectionRequestTimeout时间内依旧没有获取到可用连接，则会抛出
     * {@link org.apache.http.conn.ConnectionPoolTimeoutException}，解决方法就是适当调大一些DefaultMaxPerRoute和MaxTotal的大小。
     */
    @Value("${http.connectionRequestTimeout:500}")
    private int connectionRequestTimeout;

    /**
     * 网络连接(与服务器连接)超时时间，发起请求前创建socket连接的超时时间（以毫秒为单位）。
     * <p>
     * 测试的时候，将url改为一个不存在的url：http://test.com，超时时间过后，则会抛出
     * {@link org.apache.http.conn.ConnectTimeoutException}。
     */
    @Value("${http.connectTimeout:2000}")
    private int connectTimeout;

    /**
     * 数据传输的超时时间，socket等待数据的超时时间，响应超时时间。
     * 定义套接字超时 （SO_TIMEOUT） 以毫秒为单位，它是等待数据的超时，或者换句话说，两个连续数据包之间的最长非活动时间。避免阻塞。
     * <p>
     * 测试本地一个url，让线程sleep一段时间，来模拟返回response超时。
     */
    @Value("${http.socketTimeout:2000}")
    private int socketTimeout;

    /**
     * 池化HttpClient连接管理器，并设置最大连接数、并发连接数
     *
     * @return org.apache.http.impl.conn.PoolingHttpClientConnectionManager
     */
    @Bean(name = "httpClientConnectionManager")
    public HttpClientConnectionManager httpClientConnectionManager() {
        /**
         * timeToLive为连接存活的最大时间，每次获取的连接只要存活超时，即使服务器仍保持连接，客户端也会断开重新获取连接。客户端指的是httpclient
         * 推荐timeToLive保持默认值-1就好，这样优先使用服务器返回的KeepAlive，只要在KeepAlive时间范围内，client就不会主动关闭。
         */
        PoolingHttpClientConnectionManager poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager(-1, TimeUnit.MILLISECONDS);
        poolingHttpClientConnectionManager.setMaxTotal(maxTotal);
        poolingHttpClientConnectionManager.setDefaultMaxPerRoute(defaultMaxPerRoute);
        poolingHttpClientConnectionManager.setDefaultSocketConfig(SocketConfig.custom().setSoLinger(-1).setTcpNoDelay(true).setSoTimeout(socketTimeout).build());
        poolingHttpClientConnectionManager.setDefaultConnectionConfig(ConnectionConfig.custom().setFragmentSizeHint(-1).build());
        poolingHttpClientConnectionManager.setValidateAfterInactivity(validateAfterInactivity);
        return poolingHttpClientConnectionManager;
    }

    /**
     * 请求配置
     * 这里仅设置了超时时间
     *
     * @return org.apache.http.client.config.RequestConfig
     */
    @Bean(name = "requestConfig")
    public RequestConfig requestConfig() {
        // Builder是RequestConfig的一个内部类，通过RequestConfig的custom方法来获取到一个Builder对象，设置builder的连接信息，也可以设置proxy，cookieSpec等属性
        return RequestConfig.custom()
                .setConnectionRequestTimeout(connectionRequestTimeout)
                .setConnectTimeout(connectTimeout)
                .setSocketTimeout(socketTimeout)
                .build();
    }

    @Bean(name = "httpClientBuilder")
    public HttpClientBuilder httpClientBuilder(
            @Qualifier("httpClientConnectionManager") HttpClientConnectionManager httpClientConnectionManager,
            @Qualifier("requestConfig") RequestConfig requestConfig) {
        // HttpClientBuilder中的构造方法被protected修饰，所以这里不能直接使用new来实例化一个HttpClientBuilder，可以使用HttpClientBuilder提供的静态方法create()来获取HttpClientBuilder对象
        HttpClientBuilder httpClientBuilder = HttpClients.custom();
        // 设置连接池
        httpClientBuilder.setConnectionManager(httpClientConnectionManager);
        // 请求配置
        httpClientBuilder.setDefaultRequestConfig(requestConfig);
        /**
         * Keep-Alive策略，默认实现 {@link org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy}，
         * 读取response中的keep-alive的timeout参数，若是没有读到，那么设置为-1，代表永不过期。
         */
        ConnectionKeepAliveStrategy myStrategy = new ConnectionKeepAliveStrategy() {
            @Override
            public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
                Args.notNull(response, "HTTP response");
                final HeaderElementIterator it = new BasicHeaderElementIterator(
                        response.headerIterator(HTTP.CONN_KEEP_ALIVE));
                while (it.hasNext()) {
                    final HeaderElement he = it.nextElement();
                    final String param = he.getName();
                    final String value = he.getValue();
                    if (value != null && param.equalsIgnoreCase("timeout")) {
                        try {
                            return Long.parseLong(value) * 1000;
                        } catch (final NumberFormatException ignore) {
                        }
                    }
                }
                // 读取response中的keep-alive的timeout参数，若没读到默认设置为60秒
                return 60 * 1000;
            }
        };
        httpClientBuilder.setKeepAliveStrategy(myStrategy);

        // http幂等请求 https://developer.mozilla.org/zh-CN/docs/Glossary/Idempotent
        httpClientBuilder.setRetryHandler(new StandardHttpRequestRetryHandler(3, false));
        // 禁止重试策略。在高并发场景下建议关闭。
        httpClientBuilder.disableAutomaticRetries();

        // 为当前HttpClient实例配置定时任务，使用后台线程（线程名称见源码），定时从连接池中逐出过期、空闲连接。默认不开启
        httpClientBuilder.evictExpiredConnections();
        httpClientBuilder.evictIdleConnections(3, TimeUnit.SECONDS);

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
