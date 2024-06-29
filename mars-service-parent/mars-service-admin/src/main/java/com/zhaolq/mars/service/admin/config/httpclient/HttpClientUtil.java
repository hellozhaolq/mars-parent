package com.zhaolq.mars.service.admin.config.httpclient;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.impl.async.CloseableHttpAsyncClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.HttpStatus;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.net.URIBuilder;

import com.zhaolq.mars.common.spring.utils.SpringContext;

import lombok.extern.slf4j.Slf4j;

/**
 * 简单请求封装，可参考 https://www.wdbyte.com/tool/httpclient5/
 *
 * @Author zhaolq
 * @Date 2023/6/2 16:15:41
 */
@Slf4j
public class HttpClientUtil {
    private final CloseableHttpClient httpClient = SpringContext.getInstance().getBean("closeableHttpClient");
    private final CloseableHttpAsyncClient httpAsyncClient = SpringContext.getInstance().getBean("closeableHttpAsyncClient");
    private final RequestConfig requestConfig = SpringContext.getInstance().getBean("requestConfig");

    public String get(String url, List<NameValuePair> params, HashMap<String, Object> headers) {
        String resultContent = null;
        HttpGet httpGet = new HttpGet(url);

        try {
            // 添加请求参数
            URI uri = new URIBuilder(new URI(url)).addParameters(params).build();
            httpGet.setUri(uri);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        // 添加header
        if (ObjectUtils.isNotEmpty(headers)) {
            for (Entry<String, Object> entry : headers.entrySet()) {
                httpGet.addHeader(entry.getKey(), entry.getValue().toString());
            }
        }

        // 执行request请求
        try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
            if (response.getCode() == HttpStatus.SC_OK) {
                response.getVersion(); // HTTP/1.1
                response.getReasonPhrase(); // OK
                resultContent = EntityUtils.toString(response.getEntity());
            } else {
                new RuntimeException(response.getReasonPhrase());
            }
        } catch (NullPointerException e) {
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }

        return resultContent;
    }

    public String post(String url, List<NameValuePair> params, HashMap<String, Object> headers) {
        String result = null;
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(new UrlEncodedFormEntity(params));

        // 添加header
        if (ObjectUtils.isNotEmpty(headers)) {
            for (Entry<String, Object> entry : headers.entrySet()) {
                httpPost.addHeader(entry.getKey(), entry.getValue().toString());
            }
        }

        // 执行request请求
        try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
            if (response.getCode() == HttpStatus.SC_OK) {
                response.getVersion(); // HTTP/1.1
                response.getReasonPhrase(); // OK
                HttpEntity entity = response.getEntity();
                result = EntityUtils.toString(entity);
                // 确保流被完全消费
                EntityUtils.consume(entity);
            } else {
                new RuntimeException(response.getReasonPhrase());
            }
        } catch (NullPointerException e) {
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    public String postJSON(String url, String jsonBody, HashMap<String, Object> headers) {
        String result = null;
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(new StringEntity(jsonBody, ContentType.APPLICATION_JSON));

        // 添加header
        if (ObjectUtils.isNotEmpty(headers)) {
            for (Entry<String, Object> entry : headers.entrySet()) {
                httpPost.addHeader(entry.getKey(), entry.getValue().toString());
            }
        }

        try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
            if (response.getCode() == HttpStatus.SC_OK) {
                response.getVersion(); // HTTP/1.1
                response.getReasonPhrase(); // OK
                result = EntityUtils.toString(response.getEntity());
            } else {
                new RuntimeException(response.getReasonPhrase());
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return result;
    }
}
