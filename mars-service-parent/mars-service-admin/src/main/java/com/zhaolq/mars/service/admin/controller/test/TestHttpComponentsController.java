package com.zhaolq.mars.service.admin.controller.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zhaolq.mars.common.core.result.R;
import com.zhaolq.mars.service.admin.config.httpclient.HttpClientUtil;

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
@RequestMapping(path = "/testHttpClientUtil", consumes = {MediaType.ALL_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
public class TestHttpComponentsController {
    @Resource
    private HttpClientUtil httpClientUtil;

    @GetMapping("/testGet")
    public R<Object> test(@RequestParam(required = true) Map<String, Object> params) throws IOException {
        List<NameValuePair> list = new ArrayList<>();
        if (params != null) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                list.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
            }
        }
        return R.success(httpClientUtil.get(params.get("url").toString(), list, null));
    }
}

