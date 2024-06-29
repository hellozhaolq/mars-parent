package com.zhaolq.mars.service.admin.controller.test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zhaolq.mars.common.core.result.R;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

/**
 * 测试httpclient
 *
 * @Author zhaolq
 * @Date 2023/5/31 10:09:05
 */
@Slf4j
@RestController
@Tag(name = "测试httpclient", description = "测试httpclient")
@RequestMapping(path = "/testHttpclient", consumes = {MediaType.ALL_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
public class TestHttpComponentsController {

    @Resource
    private CloseableHttpClient httpClient;

    @Resource
    private RequestConfig config;

    @RequestMapping("/test")
    public R<Object> test() throws IOException {
        return R.success(doGet("http://www.baidu.com"));
    }

    /**
     * 不带参数的get请求，如果状态码为200，则返回body，如果不为200，则返回null
     *
     * @param url
     * @return
     * @throws Exception
     */
    public R<Object> doGet(String url) throws IOException {
        // 声明 http get 请求
        HttpGet httpGet = new HttpGet(url);
        // 装载配置信息
        httpGet.setConfig(config);
        // 无论请求执行成功还是异常，都会确保连接释放回连接管理器。EntityUtils.consume()关闭资源
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        String result = this.httpClient.execute(httpGet, responseHandler);
        return R.success(result);
    }


    /**
     * 带参数的get请求，如果状态码为200，则返回body，如果不为200，则返回null
     *
     * @param url
     * @return
     * @throws Exception
     */
    public R<Object> doGet(String url, Map<String, Object> map) throws URISyntaxException, IOException {
        URIBuilder uriBuilder = new URIBuilder(url);
        if (map != null) {
            // 遍历map，拼接请求参数
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                uriBuilder.setParameter(entry.getKey(), entry.getValue().toString());
            }
        }
        // 调用不带参数的get请求
        return this.doGet(uriBuilder.build().toString());
    }


    /**
     * 带参数的post请求
     *
     * @param url
     * @param map
     * @return
     * @throws Exception
     */
    public R<Object> doPost(String url, Map<String, Object> map) throws IOException {
        // 声明httpPost请求
        HttpPost httpPost = new HttpPost(url);
        // 加入配置信息
        httpPost.setConfig(config);
        // 判断map是否为空，不为空则进行遍历，封装from表单对象
        if (map != null) {
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                list.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
            }
            // 构造from表单对象
            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(list, "UTF-8");
            // 把表单放到post里
            httpPost.setEntity(urlEncodedFormEntity);
        }

        // 发起请求
        CloseableHttpResponse closeableHttpResponse = this.httpClient.execute(httpPost);
        String result = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");
        int responseCode = closeableHttpResponse.getStatusLine().getStatusCode();
        EntityUtils.consume(closeableHttpResponse.getEntity());
        if (responseCode != HttpStatus.OK.value()) {
            return R.failure(HttpStatus.valueOf(responseCode).getReasonPhrase());
        }
        return R.success(result);
    }

    /**
     * 不带参数post请求
     *
     * @param url
     * @return
     * @throws Exception
     */
    public R<Object> doPost(String url) throws IOException {
        return this.doPost(url, null);
    }
}

